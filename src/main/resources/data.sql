INSERT INTO roles (role_id, role_name)
VALUES ('1', 'ADMIN'),
       ('2', 'USER')
ON CONFLICT (role_id) DO UPDATE SET role_name = EXCLUDED.role_name;

INSERT INTO users (user_id, user_created_date, user_email, user_last_name, user_name, user_password, role_id)
VALUES ('a097f5fd-6918-4d43-ad59-aa377859a177', '2024-03-08 16:59:53.763', 'admin@mail.com', 'Zambrano', 'Robinson', '$2a$10$IrYHi0b16NEVV9dVainrreU8aHVJCZx4iM2lXRlnH2j7nCtEKtnIa', '1'),
        ('014930b3-4f96-4e76-9903-ea18ce357863', '2024-03-08 16:59:53.763', 'user@mail.com', 'Andrade', 'Julian', '$2a$10$IrYHi0b16NEVV9dVainrreU8aHVJCZx4iM2lXRlnH2j7nCtEKtnIa', '2')
ON CONFLICT (user_id) DO UPDATE SET user_created_date = EXCLUDED.user_created_date, user_email = EXCLUDED.user_email, user_last_name = EXCLUDED.user_last_name, user_name = EXCLUDED.user_name, user_password = EXCLUDED.user_password, role_id = EXCLUDED.role_id;

INSERT INTO lodging_types (lodging_type_id, lodging_type_name)
VALUES ('1', 'Hotel'),
       ('2', 'Hospedaje'),
       ('3', 'Residencia'),
       ('4', 'Apartamento'),
       ('5', 'Habitacion')
ON CONFLICT (lodging_type_id) DO UPDATE SET lodging_type_name = EXCLUDED.lodging_type_name;

INSERT INTO plan_transportation_types (plan_transportation_type_id, plan_transportation_type_name)
VALUES ('1', 'Aereo'),
       ('2', 'Terrestre')
ON CONFLICT (plan_transportation_type_id) DO UPDATE SET plan_transportation_type_name = EXCLUDED.plan_transportation_type_name;