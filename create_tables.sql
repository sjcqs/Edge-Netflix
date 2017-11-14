CREATE TABLE peer(
    peer_id VARCHAR(20) PRIMARY KEY,
    address VARCHAR(16) NOT NULL,
    port INT NOT NULL,
    completed TINYINT(1) NOT NULL
);
