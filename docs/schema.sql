create table parcels (
  id            uuid primary key default gen_random_uuid(),
  name          varchar(100) not null unique,
  latitude      double precision not null,
  longitude     double precision not null,
  soil_type     varchar(20) not null,
  owner_or_lead varchar(100) not null,
  created_at    timestamptz not null default now()
);



create table app_users (
  id            uuid primary key default gen_random_uuid(),
  username      varchar(50)  not null unique,
  password_hash varchar(100) not null,
  role          varchar(20)  not null check (role in ('ADMIN', 'USER')),
  created_at    timestamptz  not null default now()
);

-- Usuarios de demo (contraseñas: Admin2026! y Usuario2026!)
insert into app_users (username, password_hash, role) values
  ('admin',   '$2b$10$CKHmh6mTZWJDTQjE6HQ0.ueJo5SgEqzbh3XRQBGEuJrRHFFVkytJK', 'ADMIN'),
  ('usuario', '$2b$10$9FpTltsE.6lAyXjC29ZTCeKGi8yp/Fzvcf5IVfil4P5svMA2LJQ2u', 'USER');