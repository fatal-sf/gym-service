INSERT INTO trainers (full_name, specialization, phone, email, is_active) VALUES
('Иванов Алексей Петрович', 'Фитнес', '+79161234567', 'ivanov@example.com', true),
('Петрова Мария Сергеевна', 'Йога', '+79162345678', 'petrova@example.com', true),
('Сидоров Дмитрий Игоревич', 'Бокс', '+79163456789', 'sidorov@example.com', true),
('Кузнецова Анна Владимировна', 'Пилатес', '+79164567890', 'kuznetsova@example.com', true),
('Смирнов Олег Николаевич', 'Кроссфит', '+79165678901', 'smirnov@example.com', true);

INSERT INTO clients (name, email, phone, is_active) VALUES
('Соколов Артем Викторович', 'sokolov@example.com', '+79261234567', true),
('Волкова Елена Дмитриевна', 'volkova@example.com', '+79262345678', true),
('Козлов Иван Сергеевич', 'kozlov@example.com', '+79263456789', true),
('Новикова Ольга Петровна', 'novikova@example.com', '+79264567890', true),
('Морозов Павел Андреевич', 'morozov@example.com', '+79265678901', true);

INSERT INTO training_types (name, description, is_active) VALUES
('Функциональный тренинг', 'Тренировка на развитие силы и выносливости', true),
('Йога для начинающих', 'Базовые асаны и техники дыхания', true),
('Интенсивный бокс', 'Тренировка по боксу для среднего уровня', true),
('Пилатес реформер', 'Занятия на специальном тренажере', true),
('Кроссфит WOD', 'Высокоинтенсивная круговая тренировка', true);

INSERT INTO training_sessions (trainer_id, client_id, training_type_id, start_time, duration_minutes, is_cancelled) VALUES
(1, 1, 1, '2025-06-01 10:00:00', 60, false),
(2, 2, 2, '2025-06-01 11:30:00', 45, false),
(3, 3, 3, '2025-06-02 09:00:00', 90, false),
(4, 4, 4, '2025-06-02 18:00:00', 60, false),
(5, 5, 5, '2025-06-03 12:00:00', 120, false);