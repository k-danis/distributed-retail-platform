import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '15s', target: 30 },
        { duration: '30s', target: 30 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<30'],
    },
};

export default function () {
    const productId = 1;
    const res = http.get(`http://localhost:8222/api/v1/products/${productId}`);

    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time < 50ms': (r) => r.timings.duration < 50,
    });

    sleep(0.1);
}