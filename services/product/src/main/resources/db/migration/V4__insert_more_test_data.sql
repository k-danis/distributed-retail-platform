INSERT INTO category (id, description, name)
VALUES
    (nextval('category_seq'), 'Displays and mounts', 'Monitors'),
    (nextval('category_seq'), 'Data storage devices', 'Storage');

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES
    (nextval('product_seq'), '14-inch business laptop with 16 GB RAM and 512 GB SSD', 'Business Laptop 14', 45, 1099.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '15.6-inch business laptop with 16 GB RAM and 1 TB SSD', 'Business Laptop 15', 35, 1299.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Compact mini PC with 8 GB RAM and 256 GB SSD', 'Mini PC', 120, 349.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Dual-band WiFi 6 router with gigabit ports', 'WiFi 6 Router', 60, 149.99, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), '8-port gigabit PoE switch with metal chassis', 'PoE Switch 8', 30, 199.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Ceiling-mount WiFi 6 access point', 'Access Point AX', 40, 179.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Annual office suite license for 1 user', 'Office Suite 1Y', 300, 129.00, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Antivirus protection for 5 devices, 1 year', 'Antivirus 5Y', 180, 59.99, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Cloud backup subscription with 1 TB storage', 'Backup Cloud 1TB', 220, 99.00, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), '24-inch IPS monitor, Full HD, 75 Hz', 'Monitor 24 FHD', 90, 149.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '27-inch IPS monitor, QHD, 144 Hz', 'Monitor 27 QHD', 50, 329.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '34-inch ultrawide monitor with USB-C', 'UltraWide 34', 22, 499.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '1 TB NVMe SSD with PCIe 4.0 support', 'SSD 1TB NVMe', 150, 119.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '2 TB SATA SSD, 2.5-inch form factor', 'SSD 2TB SATA', 110, 149.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '4 TB desktop hard drive', 'HDD 4TB', 95, 109.00, (SELECT id FROM category WHERE name = 'Storage'));
INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES
    (nextval('product_seq'), '14-inch business laptop, 16 GB RAM, 512 GB SSD, i7', 'Business Laptop 14', 45, 1099.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '15.6-inch business laptop, 16 GB RAM, 1 TB SSD, i7', 'Business Laptop 15', 35, 1299.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Compact mini PC, 8 GB RAM, 256 GB SSD', 'Mini PC N100', 120, 349.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Workstation tower, 32 GB RAM, 1 TB SSD, RTX graphics', 'Workstation Pro', 18, 1899.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '13-inch lightweight Chromebook, 8 GB RAM', 'Chromebook Lite', 80, 429.00, (SELECT id FROM category WHERE name = 'Computers')),

    (nextval('product_seq'), 'Dual-band WiFi 6 router with gigabit ports', 'WiFi 6 Router', 60, 149.99, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), '8-port gigabit PoE switch, metal chassis', 'PoE Switch 8', 30, 199.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Ceiling-mount WiFi 6 access point', 'Access Point AX', 40, 179.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'USB-C to Ethernet adapter, gigabit', 'Network Adapter USB-C', 250, 24.99, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), '24-port patch panel for rack mounting', 'Patch Panel 24', 55, 89.00, (SELECT id FROM category WHERE name = 'Networking')),

    (nextval('product_seq'), 'Annual office suite license, 1 user', 'Office Suite 1Y', 300, 129.00, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Annual office suite license, 10 users', 'Office Suite 10Y', 200, 299.00, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Antivirus protection for 1 device, 1 year', 'Antivirus 1Y', 500, 39.99, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Antivirus protection for 5 devices, 1 year', 'Antivirus 5Y', 180, 59.99, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Cloud backup subscription, 1 TB, 1 year', 'Backup Cloud 1TB', 220, 99.00, (SELECT id FROM category WHERE name = 'Software')),

    (nextval('product_seq'), '24-inch IPS monitor, Full HD, 75 Hz', 'Monitor 24 FHD', 90, 149.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '27-inch IPS monitor, QHD, 144 Hz', 'Monitor 27 QHD', 50, 329.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '34-inch ultrawide monitor, USB-C', 'UltraWide 34', 22, 499.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '15.6-inch portable USB-C monitor', 'Portable Monitor 15', 65, 199.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), 'Dual monitor desk mount arm', 'Monitor Arm Dual', 140, 79.00, (SELECT id FROM category WHERE name = 'Monitors')),

    (nextval('product_seq'), '1 TB NVMe SSD, PCIe 4.0', 'SSD 1TB NVMe', 150, 119.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '2 TB SATA SSD, 2.5-inch', 'SSD 2TB SATA', 110, 149.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), 'Portable 512 GB SSD, USB-C', 'External SSD 512', 160, 89.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '4 TB desktop hard drive', 'HDD 4TB', 95, 109.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '2-bay NAS enclosure for small office', 'NAS 2-Bay', 28, 269.00, (SELECT id FROM category WHERE name = 'Storage')),

    (nextval('product_seq'), '14-inch business laptop, 32 GB RAM, 1 TB SSD, i7', 'Business Laptop 14 Plus', 28, 1299.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '13-inch ultraportable laptop, 16 GB RAM, 512 GB SSD', 'UltraBook Air 13', 52, 1149.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '17-inch mobile workstation, 64 GB RAM, 2 TB SSD', 'Mobile Workstation 17', 12, 2499.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'All-in-one desktop, 24-inch display, 16 GB RAM', 'All-in-One 24', 25, 1399.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Entry-level desktop tower, 8 GB RAM, 512 GB SSD', 'Desktop Basic', 95, 599.00, (SELECT id FROM category WHERE name = 'Computers')),

    (nextval('product_seq'), 'Mesh WiFi system, 2-pack, WiFi 6', 'Mesh WiFi 2-Pack', 38, 249.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), '10-port gigabit switch, unmanaged', 'Gigabit Switch 10', 66, 79.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Enterprise access point with PoE support', 'Access Point Enterprise', 19, 239.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'SFP fiber transceiver module, 1 GbE', 'SFP Module 1G', 120, 39.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Rack cable management panel, 1U', 'Cable Manager 1U', 88, 19.00, (SELECT id FROM category WHERE name = 'Networking'));
INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES
    (nextval('product_seq'), '14-inch business laptop, 16 GB RAM, 512 GB SSD, i7', 'Business Laptop 14', 45, 1099.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '15.6-inch business laptop, 16 GB RAM, 1 TB SSD, i7', 'Business Laptop 15', 35, 1299.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Compact mini PC, 8 GB RAM, 256 GB SSD', 'Mini PC N100', 120, 349.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Workstation tower, 32 GB RAM, 1 TB SSD, RTX graphics', 'Workstation Pro', 18, 1899.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '13-inch lightweight Chromebook, 8 GB RAM', 'Chromebook Lite', 80, 429.00, (SELECT id FROM category WHERE name = 'Computers')),

    (nextval('product_seq'), 'Dual-band WiFi 6 router with gigabit ports', 'WiFi 6 Router', 60, 149.99, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), '8-port gigabit PoE switch, metal chassis', 'PoE Switch 8', 30, 199.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Ceiling-mount WiFi 6 access point', 'Access Point AX', 40, 179.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'USB-C to Ethernet adapter, gigabit', 'Network Adapter USB-C', 250, 24.99, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), '24-port patch panel for rack mounting', 'Patch Panel 24', 55, 89.00, (SELECT id FROM category WHERE name = 'Networking')),

    (nextval('product_seq'), 'Annual office suite license, 1 user', 'Office Suite 1Y', 300, 129.00, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Annual office suite license, 10 users', 'Office Suite 10Y', 200, 299.00, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Antivirus protection for 1 device, 1 year', 'Antivirus 1Y', 500, 39.99, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Antivirus protection for 5 devices, 1 year', 'Antivirus 5Y', 180, 59.99, (SELECT id FROM category WHERE name = 'Software')),
    (nextval('product_seq'), 'Cloud backup subscription, 1 TB, 1 year', 'Backup Cloud 1TB', 220, 99.00, (SELECT id FROM category WHERE name = 'Software')),

    (nextval('product_seq'), '24-inch IPS monitor, Full HD, 75 Hz', 'Monitor 24 FHD', 90, 149.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '27-inch IPS monitor, QHD, 144 Hz', 'Monitor 27 QHD', 50, 329.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '34-inch ultrawide monitor, USB-C', 'UltraWide 34', 22, 499.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), '15.6-inch portable USB-C monitor', 'Portable Monitor 15', 65, 199.00, (SELECT id FROM category WHERE name = 'Monitors')),
    (nextval('product_seq'), 'Dual monitor desk mount arm', 'Monitor Arm Dual', 140, 79.00, (SELECT id FROM category WHERE name = 'Monitors')),

    (nextval('product_seq'), '1 TB NVMe SSD, PCIe 4.0', 'SSD 1TB NVMe', 150, 119.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '2 TB SATA SSD, 2.5-inch', 'SSD 2TB SATA', 110, 149.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), 'Portable 512 GB SSD, USB-C', 'External SSD 512', 160, 89.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '4 TB desktop hard drive', 'HDD 4TB', 95, 109.00, (SELECT id FROM category WHERE name = 'Storage')),
    (nextval('product_seq'), '2-bay NAS enclosure for small office', 'NAS 2-Bay', 28, 269.00, (SELECT id FROM category WHERE name = 'Storage')),

    (nextval('product_seq'), '14-inch business laptop, 32 GB RAM, 1 TB SSD, i7', 'Business Laptop 14 Plus', 28, 1299.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '13-inch ultraportable laptop, 16 GB RAM, 512 GB SSD', 'UltraBook Air 13', 52, 1149.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), '17-inch mobile workstation, 64 GB RAM, 2 TB SSD', 'Mobile Workstation 17', 12, 2499.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'All-in-one desktop, 24-inch display, 16 GB RAM', 'All-in-One 24', 25, 1399.00, (SELECT id FROM category WHERE name = 'Computers')),
    (nextval('product_seq'), 'Entry-level desktop tower, 8 GB RAM, 512 GB SSD', 'Desktop Basic', 95, 599.00, (SELECT id FROM category WHERE name = 'Computers')),

    (nextval('product_seq'), 'Mesh WiFi system, 2-pack, WiFi 6', 'Mesh WiFi 2-Pack', 38, 249.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), '10-port gigabit switch, unmanaged', 'Gigabit Switch 10', 66, 79.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Enterprise access point with PoE support', 'Access Point Enterprise', 19, 239.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'SFP fiber transceiver module, 1 GbE', 'SFP Module 1G', 120, 39.00, (SELECT id FROM category WHERE name = 'Networking')),
    (nextval('product_seq'), 'Rack cable management panel, 1U', 'Cable Manager 1U', 88, 19.00, (SELECT id FROM category WHERE name = 'Networking'));
