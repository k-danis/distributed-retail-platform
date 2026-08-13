import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 100 }, // Плавный разгон
        { duration: '20s', target: 500 }, // Резкий навал до 500 VUs (убиваем стандартный пул в 200 потоков)
        { duration: '40s', target: 500 }, // Удержание тяжелой полки
        { duration: '10s', target: 0 },   // Затухание
    ],
    thresholds: {
        http_req_duration: ['p(95)<400'], // Допускаем рост задержки под тяжелой базой
        http_req_failed: ['rate<0.01'],  // Ошибок быть не должно
    },
};

export default function () {
    const totalProducts = 94;
    const randomIndex = Math.floor(Math.random() * totalProducts);
    const productId = (randomIndex * 50) + 1;

    const res = http.get(`http://localhost:8222/api/v1/products/${productId}`);

    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}