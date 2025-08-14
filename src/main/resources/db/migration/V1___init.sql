CREATE TYPE txn_type AS ENUM ('EXPENSE', 'INCOME');

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    full_name TEXT NOT NULL,
    base_currency CHAR(3) NOT NULL DEFAULT 'KZT',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE users_roles (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE fx_rates (
    id BIGSERIAL PRIMARY KEY,
    rate_date DATE NOT NULL,
    base CHAR(3) NOT NULL,
    quote CHAR(3) NOT NULL,
    rate NUMERIC(19, 8) NOT NULL,
    UNIQUE (rate_date, base, quote)
);

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id),
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    limit_amount NUMERIC(19, 4) NOT NULL,
    currency CHAR(3) NOT NULL
);

