create table parcels (
  id            uuid primary key default gen_random_uuid(),
  name          varchar(100) not null unique,
  latitude      double precision not null,
  longitude     double precision not null,
  soil_type     varchar(20) not null,
  owner_or_lead varchar(100) not null,
  created_at    timestamptz not null default now()
);