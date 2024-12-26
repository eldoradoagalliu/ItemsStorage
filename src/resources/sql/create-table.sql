CREATE TABLE IF NOT EXISTS items_storage.items (
        id INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(45) NOT NULL,
        type VARCHAR(45) NOT NULL,
        price FLOAT NOT NULL,
        quantity INT NOT NULL,
        date_created DATETIME NOT NULL,
        PRIMARY KEY (id),
        UNIQUE INDEX name_UNIQUE (name ASC)
)