CREATE TABLE "franchises" (
  "id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "franchise_id" VARCHAR(10) UNIQUE,
  "poc_name" VARCHAR(100),
  "primary_number" VARCHAR(15),
  "email" VARCHAR(100) UNIQUE,
  "address" TEXT,
  "latitude" DECIMAL(10,6),
  "longitude" DECIMAL(10,6),
  "city" VARCHAR(50),
  "state" VARCHAR(50),
  "pincode" VARCHAR(10),
  "porter_hub_name" VARCHAR(100),
  "franchise_gst" VARCHAR(15),
  "franchise_pan" VARCHAR(10),
  "franchise_canceled_cheque" VARCHAR(100),
  "status" VARCHAR(10) CHECK ("status" IN ('Active', 'Inactive', 'Suspended')),
  "team_id" INT,
  "days_of_operation" VARCHAR(40),
  "start_time" TIME,
  "end_time" TIME,
  "cut_off_time" TIME,
  "kam_user" VARCHAR(100),
  "hlp_enabled" BOOLEAN,
  "radius_coverage" DECIMAL(10,2),
  "show_cr_number" BOOLEAN,
  "created_at" TIMESTAMP DEFAULT NOW(),
  "updated_at" TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_email ON "franchises" ("email");
