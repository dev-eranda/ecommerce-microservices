-- Insert data into the category table
INSERT INTO category (id, description, name)
VALUES
    (nextval('category_seq'), 'Electronics items like gadgets and appliances', 'Electronics'),
    (nextval('category_seq'), 'Furniture items for home and office', 'Furniture'),
    (nextval('category_seq'), 'Clothing and fashion accessories', 'Fashion');

-- Insert data into the product table
INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES
    (nextval('product_seq'), 'Smartphone with 128GB storage', 'Smartphone', 50, 699.99,
     (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'LED TV with 4K resolution', 'LED TV', 20, 1199.99,
     (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Ergonomic office chair', 'Office Chair', 30, 199.99,
     (SELECT id FROM category WHERE name = 'Furniture')),
    (nextval('product_seq'), 'Dining table with 6 chairs', 'Dining Table Set', 10, 899.99,
     (SELECT id FROM category WHERE name = 'Furniture')),
    (nextval('product_seq'), 'Mens casual jacket', 'Casual Jacket', 100, 59.99,
        (SELECT id FROM category WHERE name = 'Fashion')),
    (nextval('product_seq'), 'Womens leather handbag', 'Leather Handbag', 75, 129.99,
     (SELECT id FROM category WHERE name = 'Fashion'));
