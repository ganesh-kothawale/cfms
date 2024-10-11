CREATE TABLE "holidays" (
  "id" SERIAL PRIMARY KEY,
  "franchise_id" VARCHAR(10),
  "start_date" DATE,
  "end_date" DATE,
  "holiday_name" VARCHAR(50),
  "leave_type" VARCHAR(10) CHECK ("leave_type" IN ('Normal', 'Emergency')) NOT NULL,
  "backup_franchise_ids" VARCHAR(10),
  "created_at" TIMESTAMP DEFAULT NOW(),
  "updated_at" TIMESTAMP DEFAULT NOW()
);

ALTER TABLE "holidays" ADD FOREIGN KEY ("franchise_id") REFERENCES "franchises" ("franchise_id");