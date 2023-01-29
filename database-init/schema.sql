CREATE TABLE IF NOT EXISTS reciepes (
    ingredient	        VARCHAR,
    servings            INTEGER,
    additional_data     VARCHAR,
    name              VARCHAR(200) CONSTRAINT PK_NAME PRIMARY KEY,
    dish_type          VARCHAR(100)
);



