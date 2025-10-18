package com.gorman.fitnessapp

import com.gorman.fitnessapp.data.datasource.ai.dto.ProgramDto
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toEntity
import kotlinx.serialization.json.Json
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProgramMapperTest {
    private val programJson = """[
                          {
                            "program_id": "prog_mass_ppl_01",
                            "name": "Классический сплит: Тяни-Толкай-Ноги",
                            "description": "Эффективная 4-недельная программа для набора мышечной массы, построенная по принципу разделения тренировок на 'толкающие', 'тянущие' движения и день ног. Идеально подходит для проработки каждой мышечной группы раз в неделю с высоким объемом.",
                            "muscleGroup": "Тяни-Толкай-Ноги",
                            "goalType": "mass_gain",
                            "program_exercise": [
                              {
                                "detail_id": "ppl_d_001",
                                "exerciseId": "1",
                                "order": 1,
                                "dayOfWeek": "Понедельник",
                                "sets": 4,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 120
                              },
                              {
                                "detail_id": "ppl_d_002",
                                "exerciseId": "2",
                                "order": 2,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 90
                              },
                              {
                                "detail_id": "ppl_d_003",
                                "exerciseId": "10",
                                "order": 3,
                                "dayOfWeek": "Понедельник",
                                "sets": 4,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 100
                              },
                              {
                                "detail_id": "ppl_d_004",
                                "exerciseId": "11",
                                "order": 4,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 60
                              },
                              {
                                "detail_id": "ppl_d_005",
                                "exerciseId": "15",
                                "order": 5,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 70
                              },
                              {
                                "detail_id": "ppl_d_006",
                                "exerciseId": "16",
                                "order": 6,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 50
                              },
                              {
                                "detail_id": "ppl_d_007",
                                "exerciseId": "9",
                                "order": 1,
                                "dayOfWeek": "Среда",
                                "sets": 4,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 110
                              },
                              {
                                "detail_id": "ppl_d_008",
                                "exerciseId": "8",
                                "order": 2,
                                "dayOfWeek": "Среда",
                                "sets": 4,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 130
                              },
                              {
                                "detail_id": "ppl_d_009",
                                "exerciseId": "19",
                                "order": 3,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 60
                              },
                              {
                                "detail_id": "ppl_d_010",
                                "exerciseId": "13",
                                "order": 4,
                                "dayOfWeek": "Среда",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 80
                              },
                              {
                                "detail_id": "ppl_d_011",
                                "exerciseId": "14",
                                "order": 5,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 65
                              },
                              {
                                "detail_id": "ppl_d_012",
                                "exerciseId": "17",
                                "order": 6,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 20,
                                "durationSec": 0,
                                "caloriesBurned": 50
                              },
                              {
                                "detail_id": "ppl_d_013",
                                "exerciseId": "4",
                                "order": 1,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 150
                              },
                              {
                                "detail_id": "ppl_d_014",
                                "exerciseId": "5",
                                "order": 2,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 110
                              },
                              {
                                "detail_id": "ppl_d_015",
                                "exerciseId": "20",
                                "order": 3,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 80
                              },
                              {
                                "detail_id": "ppl_d_016",
                                "exerciseId": "6",
                                "order": 4,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 70
                              },
                              {
                                "detail_id": "ppl_d_017",
                                "exerciseId": "18",
                                "order": 5,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 1,
                                "durationSec": 60,
                                "caloriesBurned": 45
                              }
                            ]
                          },
                          {
                            "program_id": "prog_mass_ul_02",
                            "name": "Сплит: Верх/Низ",
                            "description": "Эта программа на 4 недели увеличивает частоту проработки мышечных групп, разделяя тренировки на дни верха и низа тела. Чередование силовых и объемных дней для верха тела стимулирует гипертрофию под разными углами.",
                            "muscleGroup": "Верх-Низ",
                            "goalType": "mass_gain",
                            "program_exercise": [
                              {
                                "detail_id": "ul_d_001",
                                "exerciseId": "1",
                                "order": 1,
                                "dayOfWeek": "Понедельник",
                                "sets": 4,
                                "repetitions": 8,
                                "durationSec": 0,
                                "caloriesBurned": 110
                              },
                              {
                                "detail_id": "ul_d_002",
                                "exerciseId": "8",
                                "order": 2,
                                "dayOfWeek": "Понедельник",
                                "sets": 4,
                                "repetitions": 8,
                                "durationSec": 0,
                                "caloriesBurned": 120
                              },
                              {
                                "detail_id": "ul_d_003",
                                "exerciseId": "10",
                                "order": 3,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 90
                              },
                              {
                                "detail_id": "ul_d_004",
                                "exerciseId": "9",
                                "order": 4,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 95
                              },
                              {
                                "detail_id": "ul_d_005",
                                "exerciseId": "13",
                                "order": 5,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 70
                              },
                              {
                                "detail_id": "ul_d_006",
                                "exerciseId": "16",
                                "order": 6,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 60
                              },
                              {
                                "detail_id": "ul_d_007",
                                "exerciseId": "4",
                                "order": 1,
                                "dayOfWeek": "Среда",
                                "sets": 4,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 150
                              },
                              {
                                "detail_id": "ul_d_008",
                                "exerciseId": "20",
                                "order": 2,
                                "dayOfWeek": "Среда",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 85
                              },
                              {
                                "detail_id": "ul_d_009",
                                "exerciseId": "5",
                                "order": 3,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 110
                              },
                              {
                                "detail_id": "ul_d_010",
                                "exerciseId": "19",
                                "order": 4,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 60
                              },
                              {
                                "detail_id": "ul_d_011",
                                "exerciseId": "17",
                                "order": 5,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 20,
                                "durationSec": 0,
                                "caloriesBurned": 50
                              },
                              {
                                "detail_id": "ul_d_012",
                                "exerciseId": "2",
                                "order": 1,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 100
                              },
                              {
                                "detail_id": "ul_d_013",
                                "exerciseId": "9",
                                "order": 2,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 115
                              },
                              {
                                "detail_id": "ul_d_014",
                                "exerciseId": "11",
                                "order": 3,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 75
                              },
                              {
                                "detail_id": "ul_d_015",
                                "exerciseId": "3",
                                "order": 4,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 65
                              },
                              {
                                "detail_id": "ul_d_016",
                                "exerciseId": "14",
                                "order": 5,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 70
                              },
                              {
                                "detail_id": "ul_d_017",
                                "exerciseId": "15",
                                "order": 6,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 75
                              }
                            ]
                          },
                          {
                            "program_id": "prog_mass_str_03",
                            "name": "Фуллбоди с силовым акцентом",
                            "description": "4-недельный цикл, нацеленный на увеличение силовых показателей в базовых упражнениях (присед, жим, тяга), что является мощным стимулом для роста мышечной массы. Каждая тренировка прорабатывает все тело с акцентом на одно ключевое движение.",
                            "muscleGroup": "Фуллбоди",
                            "goalType": "mass_gain",
                            "program_exercise": [
                              {
                                "detail_id": "str_d_001",
                                "exerciseId": "4",
                                "order": 1,
                                "dayOfWeek": "Понедельник",
                                "sets": 5,
                                "repetitions": 8,
                                "durationSec": 0,
                                "caloriesBurned": 180
                              },
                              {
                                "detail_id": "str_d_002",
                                "exerciseId": "2",
                                "order": 2,
                                "dayOfWeek": "Понедельник",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 100
                              },
                              {
                                "detail_id": "str_d_003",
                                "exerciseId": "8",
                                "order": 3,
                                "dayOfWeek": "Понедельник",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 110
                              },
                              {
                                "detail_id": "str_d_004",
                                "exerciseId": "11",
                                "order": 4,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 60
                              },
                              {
                                "detail_id": "str_d_005",
                                "exerciseId": "16",
                                "order": 5,
                                "dayOfWeek": "Понедельник",
                                "sets": 3,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 55
                              },
                              {
                                "detail_id": "str_d_006",
                                "exerciseId": "1",
                                "order": 1,
                                "dayOfWeek": "Среда",
                                "sets": 5,
                                "repetitions": 8,
                                "durationSec": 0,
                                "caloriesBurned": 150
                              },
                              {
                                "detail_id": "str_d_007",
                                "exerciseId": "20",
                                "order": 2,
                                "dayOfWeek": "Среда",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 85
                              },
                              {
                                "detail_id": "str_d_008",
                                "exerciseId": "9",
                                "order": 3,
                                "dayOfWeek": "Среда",
                                "sets": 4,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 110
                              },
                              {
                                "detail_id": "str_d_009",
                                "exerciseId": "13",
                                "order": 4,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 70
                              },
                              {
                                "detail_id": "str_d_010",
                                "exerciseId": "18",
                                "order": 5,
                                "dayOfWeek": "Среда",
                                "sets": 3,
                                "repetitions": 1,
                                "durationSec": 75,
                                "caloriesBurned": 55
                              },
                              {
                                "detail_id": "str_d_011",
                                "exerciseId": "7",
                                "order": 1,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 8,
                                "durationSec": 0,
                                "caloriesBurned": 200
                              },
                              {
                                "detail_id": "str_d_012",
                                "exerciseId": "10",
                                "order": 2,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 10,
                                "durationSec": 0,
                                "caloriesBurned": 100
                              },
                              {
                                "detail_id": "str_d_013",
                                "exerciseId": "5",
                                "order": 3,
                                "dayOfWeek": "Пятница",
                                "sets": 4,
                                "repetitions": 15,
                                "durationSec": 0,
                                "caloriesBurned": 120
                              },
                              {
                                "detail_id": "str_d_014",
                                "exerciseId": "15",
                                "order": 4,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 12,
                                "durationSec": 0,
                                "caloriesBurned": 70
                              },
                              {
                                "detail_id": "str_d_015",
                                "exerciseId": "17",
                                "order": 5,
                                "dayOfWeek": "Пятница",
                                "sets": 3,
                                "repetitions": 20,
                                "durationSec": 0,
                                "caloriesBurned": 50
                              }
                            ]
                          }
                        ]"""
    private lateinit var programDto: List<ProgramDto>

    @Before
    fun setUp() {
        val json = Json { isLenient = true; ignoreUnknownKeys = true }
        programDto = json.decodeFromString(programJson)
    }

    @Test
    fun `ProgramDto correctly maps to Domain model`() {
        // When: Вызываем маппер toDomain() на первом элементе DTO
        val programDomain = programDto.first().toDomain()

        // Then: Проверяем, что все поля смаплены корректно
        assertEquals("Классический сплит: Тяни-Толкай-Ноги", programDomain.name)
        assertEquals("Тяни-Толкай-Ноги", programDomain.muscleGroup)
        assertEquals("mass_gain", programDomain.goalType)
        assertNotNull(programDomain.exercises)
        assertEquals(17, programDomain.exercises.size)

        // Проверяем вложенный объект
        val firstExercise = programDomain.exercises.first()
        assertEquals(1, firstExercise.exerciseId)
        assertEquals("Понедельник", firstExercise.dayOfWeek)
        assertEquals(120.0f, firstExercise.caloriesBurned) // Проверяем конвертацию в Float
    }

    @Test
    fun `ProgramDto correctly maps to Entity model`() {
        val programEntity = programDto.first().toEntity()

        // Then: Проверяем, что поля смаплены, а список упражнений отсутствует
        assertEquals("Классический сплит: Тяни-Толкай-Ноги", programEntity.name)
        assertEquals("Тяни-Толкай-Ноги", programEntity.muscleGroup)
        assertEquals("mass_gain", programEntity.goalType)
        // Убеждаемся, что в Entity нет ничего лишнего (например, упражнений)
    }

    @Test
    fun `ProgramExerciseDto correctly maps to Domain model`() {
        // Given: Берем первое упражнение из DTO
        val exerciseDto = programDto.first().exercises.first()

        // When: Вызываем маппер toDomain()
        val exerciseDomain = exerciseDto.toDomain()

        // Then: Проверяем все поля, особенно конвертацию Int в Float
        assertEquals(1, exerciseDomain.exerciseId)
        assertEquals(1, exerciseDomain.order)
        assertEquals("Понедельник", exerciseDomain.dayOfWeek)
        assertEquals(4, exerciseDomain.sets)
        assertEquals(10, exerciseDomain.repetitions)
        assertEquals(0, exerciseDomain.durationSec)
        assertEquals(120.0f, exerciseDomain.caloriesBurned)
    }

    @Test
    fun `ProgramExerciseDto correctly maps to Entity model`() {
        // Given: Берем первое упражнение из DTO и задаем фиктивный ID программы
        val exerciseDto = programDto.first().exercises.first()
        val mockProgramId = 101 // ID, который должен присвоиться сущности

        // When: Вызываем маппер toEntity(), передавая ID программы
        val exerciseEntity = exerciseDto.toEntity(programId = mockProgramId)

        // Then: Проверяем, что все поля на месте, включая programId
        assertEquals(mockProgramId, exerciseEntity.programId)
        assertEquals(1, exerciseEntity.exerciseId)
        assertEquals("Понедельник", exerciseEntity.dayOfWeek)
        assertEquals(120.0f, exerciseEntity.caloriesBurned)
    }
}