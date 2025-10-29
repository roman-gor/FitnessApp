package com.gorman.fitnessapp.data.datasource.remote

import android.util.Log
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
        return try {
            val meals = client.postgrest["meal"]
                .select()
                .decodeList<MealSupabase>()
            Log.d("SUPABASE Meals", "Получен список упражнений $meals")
            meals
        } catch (e: Exception) {
            Log.e("SUPABASE Meals", "Ошибка при получении списка рационов ${e.message}")
            emptyList()
        }
    }

    override suspend fun findUserPrograms(userId: Int): List<UserProgramSupabase> {
        return try {
            val programs = client.postgrest["userprogram"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<UserProgramSupabase>()
            Log.d("SUPABASE", "Найдены программы для пользователя $userId: $programs")
            programs
        } catch (e: Exception) {
            Log.e("SUPABASE", "Ошибка при поиске программ пользователя $userId: ${e.message}")
            emptyList()
        }
    }

    override suspend fun deleteAllUserPrograms(userPrograms: List<UserProgramSupabase>) {
        if (userPrograms.isEmpty()) return
        try {
            val programIds = userPrograms.map { it.programId }
            Log.d("ProgramId", programIds.first().toString())
            val result = client.postgrest.rpc(
                function = "delete_user_program_atomic",
                parameters = mapOf(
                    "program_id" to programIds.first()
                )
            )
            Log.d("SupabaseAPI", "Успешно вызвана атомарная функция удаления программ. $result")
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при удалении программ: ${e.message}")
        }
    }

    override suspend fun insertProgram(program: ProgramSupabase): Int? {
        return try {
            val insertedProgram = client.postgrest["program"]
                .insert(program) {
                    select()
                }
                .decodeSingle<ProgramSupabase>()
            Log.d("InsertProgramId", insertedProgram.id.toString())
            insertedProgram.id
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при вставке программы: ${e.message}")
            null
        }
    }

    override suspend fun insertProgramExercise(programExercises: List<ProgramExerciseSupabase>?, programId: Int?) {
        if (programId == null || programExercises.isNullOrEmpty()) {
            Log.e("SupabaseAPI", "Program ID null или список упражнений пуст. Вставка невозможна.")
            return
        }
        try {
            val insertedData = client.postgrest["programexercise"]
                .insert(programExercises) {
                    select()
                }
                .decodeList<ProgramExerciseSupabase>()

            Log.d("ProgramExercise", "Успешно вставлено ${insertedData.size} упражнений для программы $programId")
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при вставке упражнений: ${e.message}")
        }
    }

    override suspend fun insertUserProgram(program: UserProgramSupabase) {
        try {
            client.postgrest["userprogram"]
                .insert(program)
            Log.d("SupabaseAPI", "Успешно вставлена программа пользователя.")
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при вставке программы пользователя: ${e.message}")
        }
    }

    override suspend fun insertUserProgress(userProgress: UserProgressSupabase): Int? {
        return try {
            val progressId = client.postgrest["userprogress"]
                .insert(userProgress) {
                    select()
                }
                .decodeSingle<UserProgressSupabase>()
            Log.d("SupabaseAPI", "Успешно добавлена запись прогресса пользователя.")
            progressId.id
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при добавлении записи прогресса пользователя: ${e.message}")
            null
        }
    }

    override suspend fun updateUserProgress(userProgress: UserProgressSupabase) {
        try {
            client.postgrest["userprogress"]
                .update(userProgress)
            Log.e("SUPABASE Delete", "Запись прогресса успешно обновлена")
        } catch (e: Exception) {
            Log.e("SUPABASE Delete", "Ошибка при обновлении записи прогресса: ${e.message}")
        }
    }

    override suspend fun getUserProgress(userId: Int): List<UserProgressSupabase> {
        return try {
            val queries = client.postgrest["userprogress"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<UserProgressSupabase>()
            queries
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при извлечении записи прогресса пользователя: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getUser(email: String): UserSupabase? {
        return try {
            val user = client.postgrest["usersdata"]
                .select {
                    filter {
                        eq("email", email)
                    }
                }
                .decodeSingleOrNull<UserSupabase>()
            Log.d("SUPABASE User", "Найден пользователь с email $email: $user")
            user
        } catch (e: Exception) {
            Log.e("SUPABASE User", "Ошибка при получении пользователя $email: ${e.message}")
            null
        }
    }

    override suspend fun insertUser(user: UserSupabase): Int? {
        return try {
            val insertedUser = client.postgrest["usersdata"]
                .insert(user) {
                    select()
                }
                .decodeSingle<UserSupabase>()
            Log.d("SUPABASE Insert", "Успешно вставлен пользователь с ID ${insertedUser.userId}")
            insertedUser.userId
        } catch (e: Exception) {
            Log.e("SUPABASE Insert", "Ошибка при вставке пользователя: ${e.message}")
            null
        }
    }

    override suspend fun deleteUser(user: UserSupabase) {
        try {
            client.postgrest["usersdata"]
                .delete{
                    filter {
                        eq("userId", user.userId)
                    }
                }
        } catch (e: Exception) {
            Log.e("SUPABASE Delete", "Ошибка при удалении пользователя: ${user.userId}, ${e.message}")
        }
    }

    override suspend fun updateUser(user: UserSupabase) {
        try {
            client.postgrest["usersdata"]
                .update(user)
        } catch (e: Exception) {
            Log.e("SUPABASE Delete", "Ошибка при обновлении пользователя: ${user.userId}, ${e.message}")
        }
    }

    override suspend fun insertMealPlan(
        mealPlanItemSupabase: List<MealPlanItemSupabase>,
        mealPlanTemplateSupabase: MealPlanTemplateSupabase,
        userId: Int
    ): Int? {
        return try {
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
                Log.d("SUPABASE MealPlan", "Вставлено $templateId и ${insertedItems.size} элементов.")
            }
            templateId
        } catch (e: Exception) {
            Log.e("SUPABASE MealPlan", "Ошибка при вставке плана питания: ${e.message}")
            null
        }
    }

    override suspend fun findUserMealPlanTemplate(userId: Int): Map<Int, MealPlanTemplateSupabase> {
        return try {
            val templates = client.postgrest["mealplantemplate"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<MealPlanTemplateSupabase>()

            Log.d("SUPABASE Templates", "Найдены шаблоны для пользователя $userId: ${templates.size}")
            templates.associateBy { it.templateId }
        } catch (e: Exception) {
            Log.e("SUPABASE Templates", "Ошибка при поиске шаблонов: ${e.message}")
            emptyMap()
        }
    }

    override suspend fun getProgram(programId: Int): ProgramSupabase? {
        return try {
            val program = client.postgrest["program"]
                .select {
                    filter {
                        eq("id_program", programId)
                    }
                }
                .decodeSingleOrNull<ProgramSupabase>()
            Log.d("SUPABASE Program", "Найдена программа $programId: $program")
            program
        } catch (e: Exception) {
            Log.e("SUPABASE Program", "Ошибка при получении программы $programId: ${e.message}")
            null
        }
    }

    override suspend fun getProgramExercises(programId: Int): List<ProgramExerciseSupabase> {
        return try {
            val exercises = client.postgrest["programexercise"]
                .select {
                    filter {
                        eq("programid_program", programId)
                    }
                }
                .decodeList<ProgramExerciseSupabase>()
            Log.d("SUPABASE Exercises", "Найдено ${exercises.size} упражнений для программы $programId")
            exercises
        } catch (e: Exception) {
            Log.e("SUPABASE Exercises", "Ошибка при получении упражнений программы $programId: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getUserProgram(userId: Int): UserProgramSupabase? {
        return try {
            val userProgram = client.postgrest["userprogram"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                    limit(1)
                }
                .decodeSingleOrNull<UserProgramSupabase>()

            Log.d("SUPABASE UserProgram", "Найдена программа пользователя $userId: $userProgram")
            userProgram
        } catch (e: Exception) {
            Log.e("SUPABASE UserProgram", "Ошибка при получении программы пользователя $userId: ${e.message}")
            null
        }
    }

    override suspend fun getMealPlans(userId: Int): List<MealPlanFullSupabase> {
        val templates = client.postgrest["mealplantemplate"]
            .select {
                filter {
                    eq("userid_usersdata", userId)
                }
            }
            .decodeList<MealPlanTemplateSupabase>()

        if (templates.isEmpty()) {
            Log.d("SUPABASE", "Шаблоны планов питания для пользователя $userId не найдены.")
            return emptyList()
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

        return mealPlans
    }

    override suspend fun insertWorkoutHistory(workoutHistorySupabase: WorkoutHistorySupabase): Int? {
        return try {
            val workoutHistory = client.postgrest["workouthistory"]
                .insert(workoutHistorySupabase) {
                    select()
                }
                .decodeSingle<WorkoutHistorySupabase>()
            Log.d("SupabaseAPI", "Успешно добавлена запись тренировки.")
            workoutHistory.id
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при добавлении записи тренировки пользователя: ${e.message}")
            null
        }
    }

    override suspend fun updateWorkoutHistory(workoutHistorySupabase: WorkoutHistorySupabase) {
        try {
            client.postgrest["workouthistory"]
                .update(workoutHistorySupabase)
            Log.e("SUPABASE Delete", "Запись истории тренировки успешно обновлена")
        } catch (e: Exception) {
            Log.e("SUPABASE Delete", "Ошибка при обновлении записи истории тренировки: ${e.message}")
        }
    }

    override suspend fun getWorkoutHistory(userId: Int): List<WorkoutHistorySupabase> {
        return try {
            val workoutHistory = client.postgrest["workouthistory"]
                .select {
                    filter {
                        eq("userid_usersdata", userId)
                    }
                }
                .decodeList<WorkoutHistorySupabase>()
            Log.d("SupabaseAPI", "Успешно добавлена запись тренировки.")
            workoutHistory
        } catch (e: Exception) {
            Log.e("SupabaseAPI", "Ошибка при добавлении записи тренировки пользователя: ${e.message}")
            emptyList()
        }
    }

    override suspend fun deleteMealPlan(templateId: Int) {
        try {
            client.postgrest.rpc(
                function = "delete_meal_plan",
                parameters = mapOf(
                    "template_id" to templateId
                )
            )
            Log.d("SUPABASE", "План питания с id=$templateId успешно удалён.")
        } catch (e: Exception) {
            Log.e("SUPABASE", "Ошибка при удалении плана питания: ${e.message}")
            throw e
        }
    }
}