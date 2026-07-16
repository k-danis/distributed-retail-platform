-- Additional IT‑focused categories
INSERT INTO category (id, description, name)
VALUES (nextval('category_seq'), 'Computers, laptops and tablets', 'Computers'),
       (nextval('category_seq'), 'Networking equipment and peripherals', 'Networking'),
       (nextval('category_seq'), 'Software licences and digital subscriptions', 'Software');

-- Products for the new categories
INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (nextval('product_seq'),
        '14‑inch ultra‑thin laptop with 16 GB RAM, 512 GB SSD, Intel i7',
        'UltraBook Pro 14',
        45,
        1249.00,
        (SELECT id FROM category WHERE name = 'Computers')),

       (nextval('product_seq'),
        '15.6‑inch 2‑in‑1 convertible laptop, 8 GB RAM, 256 GB SSD, touchscreen',
        'FlexPad 15',
        70,
        799.99,
        (SELECT id FROM category WHERE name = 'Computers')),

       (nextval('product_seq'),
        'Portable 256 GB SSD external hard drive, USB‑C, shock‑resistant',
        'SpeedDrive X256',
        150,
        99.95,
        (SELECT id FROM category WHERE name = 'Computers')),

       (nextval('product_seq'),
        'Wi‑Fi 6 router with dual‑band, four gigabit Ethernet ports',
        'TurboRouter 3000',
        60,
        149.99,
        (SELECT id FROM category WHERE name = 'Networking')),

       (nextval('product_seq'),
        'Power over Ethernet (PoE) switch, 8‑port gigabit, metal chassis',
        'PoE Switch 8‑Port',
        30,
        199.00,
        (SELECT id FROM category WHERE name = 'Networking')),

       (nextval('product_seq'),
        'Bluetooth mechanical keyboard with hot‑swappable switches',
        'KeyMaster Pro',
        85,
        129.99,
        (SELECT id FROM category WHERE name = 'Networking')),

       (nextval('product_seq'),
        'Annual subscription to professional office suite (10‑user license)',
        'OfficeSuite Pro – 1 Year',
        200,
        299.00,
        (SELECT id FROM category WHERE name = 'Software')),

       (nextval('product_seq'),
        'Antivirus & malware protection for up to 5 devices, 1‑year term',
        'SecureShield 5‑Device',
        180,
        59.99,
        (SELECT id FROM category WHERE name = 'Software')),

       (nextval('product_seq'),
        'Cloud development platform credits – $500 usable on compute resources',
        'DevCloud Credits $500',
        100,
        500.00,
        (SELECT id FROM category WHERE name = 'Software'));
