import request from '@/utils/request'

export function actionQuery(params) {
  return request({
    url: '/action/query',
    method: 'get',
    params
  })
}

export function actionAdd(params) {
  return request({
    url: '/action/add',
    method: 'post',
    data: params
  })
}
