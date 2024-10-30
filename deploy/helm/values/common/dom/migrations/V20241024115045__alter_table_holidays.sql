ALTER TABLE "holidays"
ADD COLUMN "holiday_id" VARCHAR(10) UNIQUE;

ALTER TABLE "holidays"
ALTER COLUMN "backup_franchise_ids" TYPE VARCHAR(64);