package com.gorman.fitnessapp.data.datasource.local

const val CREATE_TRIGGER_PROGRAM_PROGRESS = """
    CREATE TRIGGER update_program_progress
    AFTER INSERT ON WorkoutHistory
    FOR EACH ROW
    BEGIN
        UPDATE UserProgram
        SET 
            progress = (SELECT CAST(COUNT(id) AS REAL) FROM WorkoutHistory WHERE userId = NEW.userId AND programId = NEW.programId) / 100.0 
        WHERE 
            userId = NEW.userId AND programId = NEW.programId;
    END;"""

const val CREATE_TRIGGER_USER_WEIGHT = """
    CREATE TRIGGER update_user_current_weight
    AFTER INSERT ON UserProgress
    WHEN NEW.weight IS NOT NULL
    BEGIN
        UPDATE UsersData
        SET 
            weight = NEW.weight
        WHERE 
            supabaseId = NEW.supabaseId;
    END;"""

const val CREATE_TRIGGER_PROGRAM_COMPLETION = """
    CREATE TRIGGER check_program_completion
    AFTER UPDATE ON UserProgram
    FOR EACH ROW
    WHEN NEW.progress >= 1.0 AND NEW.isCompleted = 0
    BEGIN
        UPDATE UserProgram
        SET 
            isCompleted = 1, -- TRUE в SQLite это 1
            endDate = STRFTIME('%s000', 'now') -- Текущий Unix timestamp в миллисекундах
        WHERE 
            userId = NEW.userId AND 
            programId = NEW.programId;
    END;"""

//1 - Грудь
//2 - Плечи
//3 - Трицепс
//4 - Ягодицы
//5 - Поясница
//6 - Верх спины
//7 - Бицепс бедра
//8 - Сгибатели бедра
//9 - Широчайшие мышцы спины
//10 - Бицепс
//11 - Предплечья
//12 - Кор
//13 - Квадрицепсы
//14 - Икроножные мышцы
//15 - Прямая мышца живота
//16 - Поперечная мышца живота
//17 - Косые мышцы живота
////////////
//По группам:
//1-9 - Грудь
//10 - 16 - Спина
//17 - 24 - Ноги
//25 - 31 - Плечи
//32 - 40 (+2) - Кор