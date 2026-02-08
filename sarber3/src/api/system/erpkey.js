import request from '@/axios';

export const getKey = (href) => {
    return request({
        url: '/blade-system/erp/key',
        method: 'get',
        params: {
            href,
        }
    })
}