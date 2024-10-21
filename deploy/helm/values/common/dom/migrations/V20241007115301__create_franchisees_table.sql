CREATE TABLE "franchises" (
  "id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "franchise_id" VARCHAR(255) UNIQUE,
  "poc_name" VARCHAR(255),
  "primary_number" VARCHAR(20),
  "email" VARCHAR(255) UNIQUE,
  "address" VARCHAR(255),
  "latitude" DECIMAL(10,6),
  "longitude" DECIMAL(10,6),
  "city" VARCHAR(255),
  "state" VARCHAR(255),
  "pincode" VARCHAR(10),
  "porter_hub_name" VARCHAR(255),
  "franchise_gst" VARCHAR(255),
  "franchise_pan" VARCHAR(255),
  "franchise_canceled_cheque" VARCHAR(255),
  "status" VARCHAR(50) CHECK ("status" IN ('Active', 'Inactive', 'Suspended')),
  "team_id" INT,
  "days_of_operation" VARCHAR(50),
  "start_time" VARCHAR(6),
  "end_time" VARCHAR(6),
  "cut_off_time" VARCHAR(6),
  "kam_user" VARCHAR(255),
  "hlp_enabled" BOOLEAN,
  "radius_coverage" DECIMAL(5,2),
  "show_cr_number" BOOLEAN,
  "created_at" TIMESTAMP DEFAULT NOW(),
  "updated_at" TIMESTAMP DEFAULT NOW(),
  "days_of_the_week" VARCHAR(50),
  "is_active" BOOLEAN,
  "courier_partners" VARCHAR(255)
);

CREATE INDEX idx_email ON "franchises" ("email");