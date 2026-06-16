<template>
  <div class="merchant-store">
    <!-- 页面标题 -->
    <!-- 顶部用户栏 -->
<div class="top-bar">
  <div class="user-section">
    <div class="user-avatar">👨‍🍳</div>
    <div class="user-text">
      <span class="user-name">{{ merchantName }}</span>
      <span class="user-role">商家管理员</span>
    </div>
  </div>
  <el-button class="logout-button" @click="confirmLogout">
    🚪 退出登录
  </el-button>
</div>

    <div class="page-header">
      <h2>🏪 门店管理</h2>
      <p>管理您的店铺信息，展示给顾客</p>
    </div>

    <!-- 门店信息卡片 -->
    <div class="store-card">
      <div class="store-banner">
        <div class="store-logo">🍔</div>
        <div class="store-name">{{ storeInfo.name }}</div>
        <div class="store-status">营业中</div>
      </div>
      
      <el-form :model="storeInfo" label-width="100px" class="store-form">
        <el-form-item label="门店名称">
          <el-input v-model="storeInfo.name" placeholder="请输入门店名称" size="large" />
        </el-form-item>
        <el-form-item label="门店地址">
          <el-input v-model="storeInfo.address" placeholder="请输入门店地址" size="large" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="storeInfo.phone" placeholder="请输入联系电话" size="large" />
        </el-form-item>
        <el-form-item label="营业时间">
          <el-input v-model="storeInfo.hours" placeholder="例如：10:00-22:00" size="large" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" class="save-btn" @click="saveStore">
            <span>💾</span> 保存修改
          </el-button>
          <el-button size="large" @click="goDish" class="manage-btn">
            <span>🍔</span> 管理菜品
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据统计 -->
    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon">📊</div>
        <div class="stat-content">
          <div class="stat-value">{{ todayOrders }}</div>
          <div class="stat-label">今日订单</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon">💰</div>
        <div class="stat-content">
          <div class="stat-value">¥{{ todayIncome }}</div>
          <div class="stat-label">今日收入</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon">⭐</div>
        <div class="stat-content">
          <div class="stat-value">{{ storeRating }}</div>
          <div class="stat-label">店铺评分</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon">👥</div>
        <div class="stat-content">
          <div class="stat-value">{{ totalSales }}</div>
          <div class="stat-label">总销量</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ElMessageBox } from 'element-plus'

const merchantName = ref('张老板')

const router = useRouter()

const storeInfo = ref({
  name: '快乐炸鸡店',
  address: '美食街1号',
  phone: '138****1234',
  hours: '10:00 - 22:00'
})

const todayOrders = ref(28)
const todayIncome = ref(856)
const storeRating = ref(4.8)
const totalSales = ref(3456)
const confirmLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('user')
    localStorage.removeItem('token')
    router.push('/login')
    ElMessage.success('已退出登录')
  }).catch(() => {})
}

const saveStore = () => {
  ElMessage.success('门店信息已保存')
}

const goDish = () => {
  router.push('/merchant/dish')
}
</script>

<style scoped>
.merchant-store {
  padding: 0;
}

.page-header {
  margin-bottom: 24px;
}

.page-header h2 {
  font-size: 24px;
  color: #D2691E;
  margin: 0 0 8px 0;
}

.page-header p {
  color: #999;
  font-size: 14px;
  margin: 0;
}

.store-card {
  background: white;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0,0,0,0.05);
  margin-bottom: 24px;
}

.store-banner {
  background: linear-gradient(135deg, #FFE4B5 0%, #FFD699 100%);
  padding: 30px;
  display: flex;
-align-items: center;
  gap: 20px;
  border-bottom: 1px solid #FFD280;
}

.store-logo {
  width: 70px;
  height: 70px;
  background: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.store-name {
  font-size: 24px;
  font-weight: bold;
  color: #D2691E;
  flex: 1;
}

.store-status {
  background: #52C41A;
  color: white;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 14px;
}

.store-form {
  padding: 24px;
}

.save-btn {
  background: linear-gradient(135deg, #FF8C42 0%, #FF6B35 100%);
  border: none;
  margin-right: 16px;
}

.manage-btn {
  border-color: #FFD699;
  color: #FF8C42;
}

.manage-btn:hover {
  background: #FFF4E6;
  border-color: #FF8C42;
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stat-card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.05);
  transition: all 0.3s;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(255, 140, 66, 0.15);
}

.stat-icon {
  width: 50px;
  height: 50px;
  background: #FFF4E6;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #D2691E;
}

.stat-label {
  font-size: 13px;
  color: #999;
  margin-top: 4px;
}
.top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  padding: 12px 24px;
  border-radius: 16px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.user-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #FFE4B5 0%, #FFD699 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.user-text {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-size: 16px;
  font-weight: bold;
  color: #D2691E;
}

.user-role {
  font-size: 12px;
  color: #999;
}

.logout-button {
  background: #FFF4E6;
  border: 1px solid #FFE4B5;
  color: #FF6B35;
}
</style>