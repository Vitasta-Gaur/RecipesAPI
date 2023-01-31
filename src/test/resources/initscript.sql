DROP TABLE if exists reciepes;
CREATE TABLE reciepes (
    ingredient	        text,
    servings            INTEGER,
    instructions        text,
    name                VARCHAR(200) CONSTRAINT RECIPE_PK_NAME PRIMARY KEY,
    dish_type           VARCHAR(100),
    created             TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated             TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    cook_time           VARCHAR(100)
);

