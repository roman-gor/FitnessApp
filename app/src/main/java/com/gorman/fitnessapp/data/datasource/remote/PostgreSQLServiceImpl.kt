package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.postgresql.ExerciseSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealPlanFullSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealPlanItemSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealPlanTemplateSupabase
import com.gorman.fitnessapp.data.models.postgresql.ProgramExerciseSupabase
import com.gorman.fitnessapp.data.models.postgresql.ProgramSupabase
import com.gorman.fitnessapp.data.models.postgresql.UserSupabase
import com.gorman.fitnessapp.data.models.postgresql.UserProgramSupabase
import com.gorman.fitnessapp.data.models.postgresql.UserProgressSupabase
import com.gorman.fitnessapp.data.models.postgresql.WorkoutHistorySupabase
import com.gorman.fitnessapp.logger.AppLogger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import javax.inject.Inject

class PostgreSQLServiceImpl @Inject constructor(
    private val client: SupabaseClient,
    private val logger: AppLogger
) : PostgreSQLService {

    private suspend fun <T> executeRequest(
        tag: String,
        operationName: String,
        request: suspend () -> T
    ): T? {
        return try {
            val result = request()
            logger.d(tag, "Успех. $operationName")
            result
        } catch (e: Exception) {
            logger.e(tag, "Ошибка при выполнении $operationName: ${e.message}", e)
            null
        }
    }

    private suspend fun <T> executeListRequest(
        tag: String,
        operationName: String,
        request: suspend () -> List<T>
    ): List<T> {
        return executeRequest(tag, operationName, request) ?: emptyList()
    }

    override suspend fun getExercises(): List<ExerciseSupabase> {
        return executeListRequest(
            tag = "SUPABASE Exercises",
            operationName = "Получен список упражнений"
        ) {
            client.postgrest["exercise"]
                .select()
                .decodeList<ExerciseSupabase>()
        }
    }
    override suspend fun getMeals(): List<MealSupabase> {
        return executeListRequest(
            tag = "SUPABASE Meals",
            operationName = "Получен список блюд"
        ) {
            client.postgrest["meal"]
                .select()
                .decodeList<MealSupabase>()
        }
    }

    override suspend fun findUserPrograms(userId: Int): List<UserProgramSupabase> {
        return executeListRequest(
            tag = "SUPABASE UserPrograms",
            operationName = "Найдены программы для пользователя"
        ) {
            client.postgrest["userprogram"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<UserProgramSupabase>()
        }
    }

    override suspend fun deleteAllUserPrograms(userPrograms: List<UserProgramSupabase>) {
        if (userPrograms.isEmpty()) return
        executeRequest(
            tag = "SupabaseAPI",
            operationName = "Удаление программ пользователя"
        ) {
            val programIds = userPrograms.map { it.programId }
            logger.d("ProgramId", programIds.first().toString())
            client.postgrest.rpc(
                function = "delete_user_program_atomic",
                parameters = mapOf(
                    "program_id" to programIds.first()
                )
            )
        }
    }

    override suspend fun insertProgram(program: ProgramSupabase): Int? {
        return executeRequest(
            tag = "SUPABASE Insert",
            operationName = "Вставка программы тренировок"
        ) {
            val insertedProgram = client.postgrest["program"]
                .insert(program) {
                    select()
                }
                .decodeSingle<ProgramSupabase>()
            insertedProgram.id
        }
    }

    override suspend fun insertProgramExercise(programExercises: List<ProgramExerciseSupabase>?, programId: Int?) {
        if (programId == null || programExercises.isNullOrEmpty()) {
            logger.e("SupabaseAPI", "Program ID null или список упражнений пуст. Вставка невозможна.")
            return
        }
        executeRequest(
            tag = "ProgramExercise",
            operationName = "Успешно вставлено ${programExercises.size} упражнений для программы $programId"
        ) {
            client.postgrest["programexercise"]
                .insert(programExercises) {
                    select()
                }
                .decodeList<ProgramExerciseSupabase>()
        }
    }

    override suspend fun insertUserProgram(program: UserProgramSupabase) {
        executeRequest(
            tag = "SupabaseAPI",
            operationName = "Успешно вставлена программа пользователя."
        ) {
            client.postgrest["userprogram"]
                .insert(program)
        }
    }

    override suspend fun insertUserProgress(userProgress: UserProgressSupabase): Int? {
        return executeRequest(
            tag = "SupabaseAPI",
            operationName = "Успешно добавлена запись прогресса пользователя."
        ) {
            val progressId = client.postgrest["userprogress"]
                .insert(userProgress) {
                    select()
                }
                .decodeSingle<UserProgressSupabase>()
            progressId.id
        }
    }

    override suspend fun updateUserProgress(userProgress: UserProgressSupabase) {
        executeRequest(
            tag = "SUPABASE Update",
            operationName = "Запись прогресса успешно обновлена"
        ) {
            client.postgrest["userprogress"]
                .update(userProgress)
        }
    }

    override suspend fun getUserProgress(userId: Int): List<UserProgressSupabase> {
        return executeListRequest(
            tag = "SupabaseAPI",
            operationName = "Извлечение записи прогресса пользователя"
        ) {
            client.postgrest["userprogress"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<UserProgressSupabase>()
        }
    }

    override suspend fun getUser(email: String): UserSupabase? {
        return executeRequest(
            tag = "SUPABASE User",
            operationName = "Найден пользователь с email $email"
        ) {
            client.postgrest["usersdata"]
                .select {
                    filter {
                        eq("email", email)
                    }
                }
                .decodeSingleOrNull<UserSupabase>()
        }
    }

    override suspend fun insertUser(user: UserSupabase): Int? {
        return executeRequest(
            tag = "SUPABASE Insert",
            operationName = "Успешно вставлен пользователь"
        ) {
            val insertedUser = client.postgrest["usersdata"]
                .insert(user) {
                    select()
                }
                .decodeSingle<UserSupabase>()
            insertedUser.userId
        }
    }

    override suspend fun deleteUser(user: UserSupabase) {
        executeRequest(
            tag = "SUPABASE Delete",
            operationName = "Удаление пользователя: ${user.userId}"
        ) {
            client.postgrest["usersdata"]
                .delete{
                    filter {
                        eq("userId", user.userId)
                    }
                }
        }
    }

    override suspend fun updateUser(user: UserSupabase) {
        executeRequest(
            tag = "SUPABASE Update",
            operationName = "Обновление пользователя: ${user.userId}"
        ) {
            client.postgrest["usersdata"]
                .update(user)
        }
    }

    override suspend fun insertMealPlan(
        mealPlanItemSupabase: List<MealPlanItemSupabase>,
        mealPlanTemplateSupabase: MealPlanTemplateSupabase,
        userId: Int
    ): Int? {
        return executeRequest(
            tag = "SUPABASE MealPlan",
            operationName = "Вставка плана питания"
        ) {
            val templateToInsert = mealPlanTemplateSupabase.copy(userId = userId)

            val insertedTemplate = client.postgrest["mealplantemplate"]
                .insert(templateToInsert) {
                    select()
                }
                .decodeSingle<MealPlanTemplateSupabase>()

            val templateId = insertedTemplate.templateId

            if (mealPlanItemSupabase.isNotEmpty()) {
                val itemsToInsert = mealPlanItemSupabase.map { it.copy(templateId = templateId) }
                val insertedItems = client.postgrest["mealplanitem"]
                    .insert(itemsToInsert) {
                        select()
                    }
                    .decodeList<MealPlanItemSupabase>()
                logger.d("SUPABASE MealPlan", "Вставлено $templateId и ${insertedItems.size} элементов.")
            }
            templateId
        }
    }

    override suspend fun findUserMealPlanTemplate(userId: Int): Map<Int, MealPlanTemplateSupabase> {
        return executeRequest(
            tag = "SUPABASE Templates",
            operationName = "Найдены шаблоны для пользователя $userId"
        ) {
            client.postgrest["mealplantemplate"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<MealPlanTemplateSupabase>()
                .associateBy { it.templateId }
        } ?: emptyMap()
    }

    override suspend fun getProgram(programId: Int): ProgramSupabase? {
        return executeRequest(
            tag = "SUPABASE Program",
            operationName = "Найдена программа $programId"
        ) {
            client.postgrest["program"]
                .select {
                    filter {
                        eq("id_program", programId)
                    }
                }
                .decodeSingleOrNull<ProgramSupabase>()
        }
    }

    override suspend fun getProgramExercises(programId: Int): List<ProgramExerciseSupabase> {
        return executeListRequest(
            tag = "SUPABASE Exercises",
            operationName = "Найдено упражнений для программы $programId"
        ) {
            client.postgrest["programexercise"]
                .select {
                    filter {
                        eq("programid_program", programId)
                    }
                }
                .decodeList<ProgramExerciseSupabase>()
        }
    }

    override suspend fun getUserProgram(userId: Int): UserProgramSupabase? {
        return executeRequest(
            tag = "SUPABASE UserProgram",
            operationName = "Найдена программа пользователя $userId"
        ) {
            client.postgrest["userprogram"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                    limit(1)
                }
                .decodeSingleOrNull<UserProgramSupabase>()
        }
    }

    override suspend fun getMealPlans(userId: Int): List<MealPlanFullSupabase> {
        return executeListRequest(
            tag = "SUPABASE",
            operationName = "Получение планов питания для пользователя $userId"
        ) {
            val templates = client.postgrest["mealplantemplate"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<MealPlanTemplateSupabase>()

            if (templates.isEmpty()) {
                logger.d("SUPABASE", "Шаблоны планов питания для пользователя $userId не найдены.")
                return@executeListRequest emptyList()
            }

            val mealPlans = mutableListOf<MealPlanFullSupabase>()

            for (template in templates) {
                val items = client.postgrest["mealplanitem"]
                    .select {
                        filter {
                            eq("templateid_mealplantemplate", template.templateId)
                        }
                    }
                    .decodeList<MealPlanItemSupabase>()
                mealPlans.add(MealPlanFullSupabase(template, items))
            }
            mealPlans
        }
    }

    override suspend fun insertWorkoutHistory(workoutHistorySupabase: WorkoutHistorySupabase): Int? {
        return executeRequest(
            tag = "SupabaseAPI",
            operationName = "Успешно добавлена запись тренировки."
        ) {
            val workoutHistory = client.postgrest["workouthistory"]
                .insert(workoutHistorySupabase) {
                    select()
                }
                .decodeSingle<WorkoutHistorySupabase>()
            workoutHistory.id
        }
    }

    override suspend fun updateWorkoutHistory(workoutHistorySupabase: WorkoutHistorySupabase) {
        executeRequest(
            tag = "SUPABASE Update",
            operationName = "Запись истории тренировки успешно обновлена"
        ) {
            client.postgrest["workouthistory"]
                .update(workoutHistorySupabase)
        }
    }

    override suspend fun getWorkoutHistory(userId: Int): List<WorkoutHistorySupabase> {
        return executeListRequest(
            tag = "SupabaseAPI",
            operationName = "Получение истории тренировок"
        ) {
            client.postgrest["workouthistory"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<WorkoutHistorySupabase>()
        }
    }

    override suspend fun deleteMealPlan(templateId: Int) {
        executeRequest(
            tag = "SUPABASE",
            operationName = "План питания с id=$templateId успешно удалён."
        ) {
            client.postgrest.rpc(
                function = "delete_meal_plan",
                parameters = mapOf(
                    "template_id" to templateId
                )
            )
        }
    }
}