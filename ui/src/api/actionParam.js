import request from '@/utils/request'

export function actionParamList(params) {
  return request({
    url: 'action/param/list',
    method: 'get',
    params
  })
}
