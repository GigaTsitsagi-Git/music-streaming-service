INSERT INTO users (username, email, created_date, created_datetime) VALUES ('john', 'john@example.com', CURRENT_DATE, CURRENT_TIMESTAMP);
INSERT INTO users (username, email, created_date, created_datetime) VALUES ('mary', 'mary@example.com', CURRENT_DATE, CURRENT_TIMESTAMP);
INSERT INTO artists (name) VALUES ('Taylor Swift');
INSERT INTO artists (name) VALUES ('The Weeknd');
INSERT INTO albums (title, artist_id) VALUES ('Midnights', (SELECT id FROM artists WHERE name='Taylor Swift'));
INSERT INTO albums (title, artist_id) VALUES ('Dawn FM', (SELECT id FROM artists WHERE name='The Weeknd'));
INSERT INTO songs (title, artist_id, album_id) VALUES ('Anti-Hero', (SELECT id FROM artists WHERE name='Taylor Swift'), (SELECT id FROM albums WHERE title='Midnights'));
INSERT INTO songs (title, artist_id, album_id) VALUES ('Gasoline', (SELECT id FROM artists WHERE name='The Weeknd'), (SELECT id FROM albums WHERE title='Dawn FM'));
INSERT INTO subscription_plans (name, price) VALUES ('Premium', 9.99);
INSERT INTO genres (name) VALUES ('Pop');

UPDATE users SET email='john.doe@example.com' WHERE username='john';
UPDATE users SET email='mary.smith@example.com' WHERE username='mary';
UPDATE artists SET name='The Weeknd (XO)' WHERE name='The Weeknd';
UPDATE albums SET title='Midnights (3am Edition)' WHERE title='Midnights';
UPDATE songs SET title='Anti Hero' WHERE title='Anti-Hero';
UPDATE subscription_plans SET price=10.99 WHERE name='Premium';
UPDATE genres SET name='Synth Pop' WHERE name='Pop';
UPDATE songs SET album_id=(SELECT id FROM albums WHERE title='Midnights (3am Edition)') WHERE title='Anti Hero';
UPDATE albums SET artist_id=(SELECT id FROM artists WHERE name='Taylor Swift') WHERE title='Midnights (3am Edition)';
UPDATE users SET username='johnny' WHERE username='john';

DELETE FROM song_genres WHERE song_id IN (SELECT id FROM songs WHERE title='Gasoline');
DELETE FROM playlist_items WHERE song_id IN (SELECT id FROM songs WHERE title='Gasoline');
DELETE FROM songs WHERE title='Gasoline';
DELETE FROM albums WHERE title='Dawn FM';
DELETE FROM artists WHERE name='The Weeknd (XO)';
DELETE FROM user_subscriptions WHERE user_id IN (SELECT id FROM users WHERE username='mary');
DELETE FROM playlists WHERE owner_user_id IN (SELECT id FROM users WHERE username='mary');
DELETE FROM streaming_sessions WHERE user_id IN (SELECT id FROM users WHERE username='mary');
DELETE FROM users WHERE username='mary';
DELETE FROM genres WHERE name='Synth Pop';

ALTER TABLE users ADD COLUMN phone VARCHAR(30) NULL;
ALTER TABLE playlists ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE songs ADD COLUMN duration_seconds INT NULL;
ALTER TABLE subscription_plans ADD COLUMN description VARCHAR(255) NULL;
ALTER TABLE playlist_items ADD CONSTRAINT chk_position CHECK (position > 0);

SELECT u.username, u.email, p.name AS playlist_name, pi.position, s.title AS song_title, ar.name AS artist_name,
       al.title AS album_title, ss.started_at AS session_started, cs.title AS current_song_title,
       plan.name AS plan_name, plan.price AS plan_price, g.name AS genre_name
FROM users u
LEFT JOIN playlists p ON p.owner_user_id = u.id
LEFT JOIN playlist_items pi ON pi.playlist_id = p.id
LEFT JOIN songs s ON s.id = pi.song_id
LEFT JOIN artists ar ON ar.id = s.artist_id
LEFT JOIN albums al ON al.id = s.album_id
LEFT JOIN streaming_sessions ss ON ss.user_id = u.id
LEFT JOIN songs cs ON cs.id = ss.current_song_id
LEFT JOIN user_subscriptions us ON us.user_id = u.id
LEFT JOIN subscription_plans plan ON plan.id = us.plan_id
LEFT JOIN song_genres sg ON sg.song_id = s.id
LEFT JOIN genres g ON g.id = sg.genre_id;

SELECT u.username, p.name FROM users u INNER JOIN playlists p ON p.owner_user_id = u.id;
SELECT u.username, p.name FROM users u LEFT JOIN playlists p ON p.owner_user_id = u.id;
SELECT u.username, p.name FROM users u RIGHT JOIN playlists p ON p.owner_user_id = u.id;
SELECT s.title, g.name FROM songs s LEFT JOIN song_genres sg ON sg.song_id=s.id LEFT JOIN genres g ON g.id=sg.genre_id;
SELECT s.title, g.name FROM songs s LEFT JOIN song_genres sg ON sg.song_id=s.id LEFT JOIN genres g ON g.id=sg.genre_id
UNION
SELECT s.title, g.name FROM songs s RIGHT JOIN song_genres sg ON sg.song_id=s.id RIGHT JOIN genres g ON g.id=sg.genre_id;

SELECT ar.name, COUNT(*) AS songs_count FROM songs s JOIN artists ar ON ar.id=s.artist_id GROUP BY ar.name;
SELECT al.title, COUNT(*) AS tracks FROM songs s JOIN albums al ON al.id=s.album_id GROUP BY al.title;
SELECT u.username, COUNT(*) AS playlists_count FROM users u LEFT JOIN playlists p ON p.owner_user_id=u.id GROUP BY u.username;
SELECT p.name, COUNT(*) AS items FROM playlists p LEFT JOIN playlist_items i ON i.playlist_id=p.id GROUP BY p.name;
SELECT g.name, COUNT(*) AS songs FROM genres g LEFT JOIN song_genres sg ON sg.genre_id=g.id GROUP BY g.name;
SELECT plan.name, AVG(plan.price) AS avg_price FROM subscription_plans plan GROUP BY plan.name;
SELECT DATE(ss.started_at) AS day, COUNT(*) AS sessions FROM streaming_sessions ss GROUP BY DATE(ss.started_at);

SELECT ar.name, COUNT(*) AS songs_count FROM songs s JOIN artists ar ON ar.id=s.artist_id GROUP BY ar.name HAVING COUNT(*) >= 1;
SELECT al.title, COUNT(*) AS tracks FROM songs s JOIN albums al ON al.id=s.album_id GROUP BY al.title HAVING COUNT(*) >= 1;
SELECT u.username, COUNT(p.id) AS playlists_count FROM users u LEFT JOIN playlists p ON p.owner_user_id=u.id GROUP BY u.username HAVING COUNT(p.id) >= 1;
SELECT p.name, COUNT(i.id) AS items FROM playlists p LEFT JOIN playlist_items i ON i.playlist_id=p.id GROUP BY p.name HAVING COUNT(i.id) >= 1;
SELECT g.name, COUNT(sg.song_id) AS songs FROM genres g LEFT JOIN song_genres sg ON sg.genre_id=g.id GROUP BY g.name HAVING COUNT(sg.song_id) >= 1;
SELECT plan.name, COUNT(us.id) AS subs FROM subscription_plans plan LEFT JOIN user_subscriptions us ON us.plan_id=plan.id GROUP BY plan.name HAVING COUNT(us.id) >= 1;
SELECT DATE(ss.started_at) AS day, COUNT(*) AS sessions FROM streaming_sessions ss GROUP BY DATE(ss.started_at) HAVING COUNT(*) >= 1;


