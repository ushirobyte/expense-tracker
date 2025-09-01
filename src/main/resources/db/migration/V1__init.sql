-- DEV-safe: гарантированно создаём enum в нужной схеме
DROP TYPE IF EXISTS public.txn_type CASCADE;
CREATE TYPE public.txn_type AS ENUM ('EXPENSE','INCOME');

-- БАЗОВЫЕ ТАБЛИЦЫ
CREATE TABLE IF NOT EXISTS public.users (
                                            id BIGSERIAL PRIMARY KEY,
                                            email TEXT UNIQUE NOT NULL,
                                            password_hash TEXT NOT NULL,
                                            full_name TEXT NOT NULL,
                                            base_currency CHAR(3) NOT NULL DEFAULT 'KZT',
                                            created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS public.roles (
                                            id BIGSERIAL PRIMARY KEY,
                                            name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS public.user_roles (
                                                 user_id BIGINT NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
                                                 role_id BIGINT NOT NULL REFERENCES public.roles(id) ON DELETE CASCADE,
                                                 PRIMARY KEY(user_id, role_id)
);

CREATE TABLE IF NOT EXISTS public.categories (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 user_id BIGINT REFERENCES public.users(id) ON DELETE CASCADE,
                                                 name TEXT NOT NULL,
                                                 type public.txn_type NOT NULL
);

CREATE TABLE IF NOT EXISTS public.fx_rates (
                                               id BIGSERIAL PRIMARY KEY,
                                               rate_date DATE NOT NULL,
                                               base CHAR(3) NOT NULL,
                                               quote CHAR(3) NOT NULL,
                                               rate NUMERIC(19,8) NOT NULL,
                                               UNIQUE(rate_date, base, quote)
);

-- ЗАВИСИМЫЕ
CREATE TABLE IF NOT EXISTS public.transactions (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   user_id BIGINT NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
                                                   category_id BIGINT REFERENCES public.categories(id),
                                                   type public.txn_type NOT NULL,
                                                   amount NUMERIC(19,4) NOT NULL,
                                                   currency CHAR(3) NOT NULL,
                                                   txn_time TIMESTAMPTZ NOT NULL,
                                                   note TEXT
);

CREATE TABLE IF NOT EXISTS public.budgets (
                                              id BIGSERIAL PRIMARY KEY,
                                              user_id BIGINT NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
                                              category_id BIGINT REFERENCES public.categories(id),
                                              period_start DATE NOT NULL,
                                              period_end DATE NOT NULL,
                                              limit_amount NUMERIC(19,4) NOT NULL,
                                              currency CHAR(3) NOT NULL
);

-- СИДЫ
INSERT INTO public.roles(name) VALUES ('ROLE_USER') ON CONFLICT (name) DO NOTHING;
INSERT INTO public.roles(name) VALUES ('ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
