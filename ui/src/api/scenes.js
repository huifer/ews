import request from '@/utils/request'

export function scenesQuery(params) {
  return request({
    url: '/scenes/query',
    method: 'get',
    params
  })
}

export function scenesAdd(data) {
  return request({
    url: '/scenes/add',
    method: 'post',
    data: data
  })
}
export function scenesList() {
  return request({
    url: '/scenes/list',
    method: 'get',
  })
}
