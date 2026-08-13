import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 100 }, // Разгон до 100 пользователей
        { duration: '30s', target: 300 }, // Нагрузка 300 VUs
        { duration: '10s', target: 0 },   // Затухание
    ],
    thresholds: {
        http_req_duration: ['p(95)<200'],
    },
};

export default function () {
    const totalProducts = 94;

    const randomIndex = Math.floor(Math.random() * totalProducts);

    const productId = (randomIndex * 50) + 1;

    const res = http.get(`http://localhost:8222/api/v1/products/${productId}`);

    check(res, {
        'status is 200': (r) => r.status === 200
    });
}