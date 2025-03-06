-- Add phone_number and phone_verified columns to entreprise table
ALTER TABLE entreprise 
ADD COLUMN IF NOT EXISTS phone_number VARCHAR(20),
ADD COLUMN IF NOT EXISTS phone_verified BOOLEAN DEFAULT FALSE,
ADD COLUMN IF NOT EXISTS email_verified BOOLEAN DEFAULT FALSE;

-- Drop email column if it exists
ALTER TABLE entreprise DROP COLUMN IF EXISTS email;

-- Drop email_verified column if it exists
ALTER TABLE entreprise DROP COLUMN IF EXISTS email_verified; 