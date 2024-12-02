import request from '@/utils/request'

// 查询分组列表
export function urlList(query,body) {
  return request({
    url: '/shortUrl/url/list',
    method: 'post',
    params: query,
    data: body
  })
}


// 新增短链接
export function addUrl(body) {
  return request({
    url: '/shortUrl/url/add',
    method: 'post',
    data: body
  })
}


// 修改短链接
export function updateUrl(body) {
  return request({
    url: '/shortUrl/url/update',
    method: 'post',
    data: body
  })
}


// 修改短链接状态
export function changeStatus(body) {
  return request({
    url: '/shortUrl/url/updateStatus',
    method: 'post',
    data: body
  })
}


// 删除短链接
export function delUrl(body) {
  return request({
    url: '/shortUrl/url/del',
    method: 'post',
    data: body
  })
}

// 获取统计结果
export function getStatistics(body) {
  return request({
    url: '/shortUrl/url/statistics',
    method: 'post',
    data: body
  })
}


export function encodeGo(body) {
  return request({
    url: '/shortUrl/url/encodeGo',
    method: 'post',
    data: body
  })
}


export function open(body) {
  return request({
    url: '/shortUrl/url/openUrl',
    method: 'post',
    data: body
  })
}


// 查询艺术二维码列表
export function qrList(query) {
  return request({
    url: '/artQRCode/list',
    method: 'post',
    params: query,
    data: null
  })
}

// 生成艺术二维码
export function qrAdd(body) {
  return request({
    url: '/artQRCode/add',
    method: 'post',
    data: body,
  })
}

// 获取用户生成艺术二维码信息
export function userData() {
  return request({
    url: '/artQRCode/userData',
    method: 'post',
    data: null
  })
}

// 删除艺术二维码信息
export function delCode(body) {
  return request({
    url: '/artQRCode/delCode',
    method: 'post',
    data: body
  })
}