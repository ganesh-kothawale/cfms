CREATE TABLE "holidays" (
  "id" SERIAL PRIMARY KEY,
  "franchise_id" varchar,
  "start_date" date,
  "end_date" date,
  "holiday_name" varchar,
  "leave_type" varchar CHECK ("leave_type" IN ('Normal', 'Emergency')) NOT NULL,
  "backup_franchise_ids" varchar,
  "created_at" timestamp DEFAULT now(),
  "updated_at" timestamp DEFAULT now()
);