CREATE INDEX idx_file_uploaded_at ON file_entries(uploaded_at);
CREATE INDEX idx_file_size_bytes ON file_entries(size_bytes);
CREATE INDEX idx_file_deleted ON file_entries(deleted);

-- Case-insensitive search support for Postgres in the future
CREATE INDEX idx_file_original_name_lower ON file_entries(lower(original_name));