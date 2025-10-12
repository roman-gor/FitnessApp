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
            id = NEW.userId AND 
            NEW.date >= COALESCE((SELECT MAX(date) FROM UserProgress WHERE userId = NEW.userId), 0);
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