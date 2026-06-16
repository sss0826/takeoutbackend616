<template>
  <div>
    <h2>👤 我的信息</h2>
    
    <el-descriptions :column="1" border>
      <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ userInfo.phone }}</el-descriptions-item>
      <el-descriptions-item label="收货地址">{{ userInfo.address }}</el-descriptions-item>
    </el-descriptions>

    <el-button type="primary" @click="editProfile" style="margin-top: 20px">编辑资料</el-button>
    <el-button type="danger" @click="logout" style="margin-top: 20px">退出登录</el-button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userInfo = ref({
  username: '吃货小明',
  phone: '138****8888',
  address: '北京市朝阳区XX路123号'
})

const editProfile = () => {
  ElMessage.info('编辑资料功能开发中')
}

const logout = () => {
  router.push('/login')
}
</script>