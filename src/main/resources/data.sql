-- users
INSERT INTO users (id, name, age, address, email, password, image, create_at) VALUES
(1, '佐藤 花子', 34, '東京都渋谷区', 'hanako@example.com', 'password_hash_001', '/images/users/user1.jpg', CURRENT_TIMESTAMP),
(2, '鈴木 太郎', 42, '大阪府大阪市', 'taro@example.com', 'password_hash_002', '/images/users/user2.jpg', CURRENT_TIMESTAMP),
(3, '高橋 美咲', 29, '神奈川県横浜市', 'misaki@example.com', 'password_hash_003', '/images/users/user3.jpg', CURRENT_TIMESTAMP);
 
-- producers
INSERT INTO producers (id, name, body, area, image, email, phone, sns_link, create_at) VALUES
(1, '富良野ミルクファーム', '富良野の自然の中で育った乳牛の牛乳を使い、濃厚な乳製品を製造しています。', '富良野', '/images/producers/furano_milk.jpg', 'info@furano-milk.example.com', '0167-00-0001', 'https://example.com/furano-milk', CURRENT_TIMESTAMP),
(2, '函館海鮮工房', '函館近海で獲れた海産物を中心に、新鮮な北海道の味を全国へ届けています。', '函館', '/images/producers/hakodate_seafood.jpg', 'info@hakodate-seafood.example.com', '0138-00-0002', 'https://example.com/hakodate-seafood', CURRENT_TIMESTAMP),
(3, '十勝ベジタブルガーデン', '十勝平野の寒暖差を活かし、甘みの強い野菜を栽培しています。', '十勝', '/images/producers/tokachi_vegetable.jpg', 'info@tokachi-veg.example.com', '0155-00-0003', 'https://example.com/tokachi-veg', CURRENT_TIMESTAMP),
(4, '小樽スイーツラボ', '北海道産素材を使った焼き菓子やスイーツを開発しています。', '小樽', '/images/producers/otaru_sweets.jpg', 'info@otaru-sweets.example.com', '0134-00-0004', 'https://example.com/otaru-sweets', CURRENT_TIMESTAMP);
 
-- categories
INSERT INTO categories (id, name, description) VALUES
(1, '乳製品', 'チーズ、バター、ヨーグルトなど北海道産の乳製品'),
(2, '海産物', 'カニ、いくら、ホタテなど北海道の海の幸'),
(3, '農産物', 'じゃがいも、とうもろこし、玉ねぎなど北海道の農産物'),
(4, 'スイーツ', '北海道産素材を使用した菓子・デザート'),
(5, 'ギフトセット', '贈答用に適した詰め合わせ商品');
 
-- seasons
INSERT INTO seasons (id, name, start_month, end_month) VALUES
(1, '春', 3, 5),
(2, '夏', 6, 8),
(3, '秋', 9, 11),
(4, '冬', 12, 2),
(5, '通年', 1, 12);
 
-- gift_tags
INSERT INTO gift_tags (id, name) VALUES
(1, '家族向け'),
(2, '友人向け'),
(3, 'お祝い'),
(4, 'お中元'),
(5, 'お歳暮'),
(6, '自分へのご褒美');
 
-- products
INSERT INTO products (
    id, producer_id, name, body, price, stock, origin_area, image,
    recommendation_score, create_at, update_at, category_id
) VALUES
(1, 1, '富良野濃厚チーズセット', '富良野産牛乳を使用したチーズの詰め合わせです。ワインのお供にも適しています。', 4200, 30, '富良野', '/images/products/cheese_set.jpg', 4.8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
(2, 1, '北海道ミルクバター', '香り豊かな北海道産バター。パンや料理に使いやすい定番商品です。', 1800, 50, '富良野', '/images/products/milk_butter.jpg', 4.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
(3, 2, '函館いくら醤油漬け', '函館の海の幸を味わえる、濃厚ないくら醤油漬けです。', 5600, 20, '函館', '/images/products/ikura.jpg', 4.9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
(4, 2, '北海道ホタテ貝柱', '肉厚で甘みのあるホタテ貝柱。刺身やバター焼きにおすすめです。', 4800, 25, '函館', '/images/products/scallop.jpg', 4.7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
(5, 3, '十勝じゃがいも詰め合わせ', '十勝産のじゃがいもを複数品種で楽しめる詰め合わせです。', 2500, 80, '十勝', '/images/products/potato_set.jpg', 4.2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
(6, 3, '十勝とうもろこしセット', '甘みの強い十勝産とうもろこしの季節限定セットです。', 3200, 40, '十勝', '/images/products/corn_set.jpg', 4.6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
(7, 4, '小樽チーズケーキ', '北海道産チーズを使用した、しっとり濃厚なチーズケーキです。', 3000, 35, '小樽', '/images/products/cheesecake.jpg', 4.5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),
(8, 4, '北海道スイーツギフト', '焼き菓子とチーズケーキを組み合わせたギフト向けセットです。', 5200, 15, '小樽', '/images/products/sweets_gift.jpg', 4.8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5);
 
-- products_categories
INSERT INTO products_categories (product_id, category_id) VALUES
(1, 1),
(1, 5),
(2, 1),
(3, 2),
(3, 5),
(4, 2),
(5, 3),
(6, 3),
(7, 4),
(8, 4),
(8, 5);
 
-- product_seasons
INSERT INTO product_seasons (product_id, season_id) VALUES
(1, 5),
(2, 5),
(3, 4),
(4, 5),
(5, 3),
(6, 2),
(7, 5),
(8, 5);
 
-- orders
INSERT INTO orders (id, user_id, price, payment_method, shipping_address, order_at) VALUES
(1, 1, 9800, 'credit_card', '東京都渋谷区1-1-1', CURRENT_TIMESTAMP),
(2, 2, 5700, 'bank_transfer', '大阪府大阪市2-2-2', CURRENT_TIMESTAMP),
(3, 3, 5200, 'convenience_store', '神奈川県横浜市3-3-3', CURRENT_TIMESTAMP);
 
-- order_items
INSERT INTO order_items (id, order_id, product_id, quantity) VALUES
(1, 1, 3, 1),
(2, 1, 1, 1),
(3, 2, 5, 1),
(4, 2, 6, 1),
(5, 3, 8, 1);
 
-- cart_items
INSERT INTO cart_items (id, user_id, product_id, quantity) VALUES
(1, 1, 7, 1),
(2, 2, 4, 2),
(3, 3, 1, 1);
 
-- favorite_products
INSERT INTO favorite_products (id, user_id, product_id, create_at) VALUES
(1, 1, 1, CURRENT_TIMESTAMP),
(2, 1, 3, CURRENT_TIMESTAMP),
(3, 2, 6, CURRENT_TIMESTAMP),
(4, 3, 8, CURRENT_TIMESTAMP);
 
-- product_views
INSERT INTO product_views (id, user_id, product_id, view_at) VALUES
(1, 1, 1, CURRENT_TIMESTAMP),
(2, 1, 3, CURRENT_TIMESTAMP),
(3, 1, 8, CURRENT_TIMESTAMP),
(4, 2, 5, CURRENT_TIMESTAMP),
(5, 2, 6, CURRENT_TIMESTAMP),
(6, 3, 7, CURRENT_TIMESTAMP);
 
-- gift_diagnosis_logs
INSERT INTO gift_diagnosis_logs (log_id, user_id, result, diagnosis_at) VALUES
(1, 1, '家族向けには、北海道スイーツギフトと富良野濃厚チーズセットがおすすめです。', CURRENT_TIMESTAMP),
(2, 2, '季節感を重視する場合は、十勝とうもろこしセットがおすすめです。', CURRENT_TIMESTAMP),
(3, 3, 'お祝い用には、函館いくら醤油漬けや北海道スイーツギフトがおすすめです。', CURRENT_TIMESTAMP);
 
-- product_gift_tags
INSERT INTO product_gift_tags (product_id, tag_id) VALUES
(1, 2),
(1, 3),
(3, 3),
(3, 5),
(6, 4),
(7, 6),
(8, 1),
(8, 3),
(8, 5);
 
-- recommendation_logs
INSERT INTO recommendation_logs (id, user_id, product_id, create_at, recommendation_reason) VALUES
(1, 1, 8, CURRENT_TIMESTAMP, 'スイーツ商品の閲覧履歴があるため'),
(2, 1, 1, CURRENT_TIMESTAMP, '乳製品カテゴリへの関心が高いため'),
(3, 2, 6, CURRENT_TIMESTAMP, '夏季の旬商品としておすすめ'),
(4, 3, 3, CURRENT_TIMESTAMP, 'ギフト診断結果に基づくおすすめ');
 
-- product_relations
INSERT INTO product_relations (id, product_id, related_product_id) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 4),
(4, 4, 3),
(5, 7, 8),
(6, 8, 7),
(7, 5, 6),
(8, 6, 5);