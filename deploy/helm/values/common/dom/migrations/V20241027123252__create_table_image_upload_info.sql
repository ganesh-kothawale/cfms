CREATE TABLE IF NOT EXISTS image_upload_info(
    id            SERIAL PRIMARY KEY,
    external_uuid UUID         NOT NULL UNIQUE,
    bucket_name   VARCHAR(32)  NOT NULL,
    object_key    VARCHAR(256) NOT NULL,
    use_case      VARCHAR(64)  NOT NULL,
    is_uploaded   BOOLEAN      NULL,
    created_at    timestamp    NOT NULL,
    updated_at    timestamp    NOT NULL
);