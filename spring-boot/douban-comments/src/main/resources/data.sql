DROP TABLE IF EXISTS review;

CREATE TABLE review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(32) NOT NULL,
    stars INT NOT NULL,
    create_time DATE NOT NULL,
    short_review TEXT NOT NULL
);

INSERT INTO review (user_name, stars, create_time, short_review) VALUES
    ('Aliko', '4', '2020-11-03', 'I like this movie');