import request from '@/utils/request'

// 查询分组列表
export function listType(query,body) {
  return request({
    url: '/shortUrl/group/list',
    method: 'post',
    params: query,
    data: body
  })
}

// 查询所有分组列表
export function listSelect() {
  return request({
    url: '/shortUrl/group/allList',
    method: 'post',
    data: null
  })
}


// 查询字典类型详细
export function getType(id) {
  return request({
    url: '/shortUrl/group/list',
    method: 'post',
    data: id
  })
}

// 新增分组
export function addType(data) {
  return request({
    url: '/shortUrl/group/add',
    method: 'post',
    data: data
  })
}

// 修改分组
export function updateType(data) {
  return request({
    url: '/shortUrl/group/update',
    method: 'post',
    data: data
  })
}

// 删除分组
export function delType(id) {
  return request({
    url: '/shortUrl/group/del',
    method: 'post',
    data: id
  })
}

// 刷新字典缓存
export function refreshCache() {
  return request({
    url: '/system/dict/type/refreshCache',
    method: 'delete'
  })
}

// 获取字典选择框列表
export function optionselect() {
  return request({
    url: '/system/dict/type/optionselect',
    method: 'get'
  })
}