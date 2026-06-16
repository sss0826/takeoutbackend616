<template>
  <div class="rider-delivery">
    <!-- 顶部骑手信息栏 -->
    <div class="rider-top-bar">
      <div class="rider-info">
        <div class="rider-avatar">🛵</div>
        <div class="rider-detail">
          <div class="rider-name">{{ riderName }}</div>
          <div class="rider-badge">金牌骑手 · 好评率99%</div>
        </div>
      </div>
      <div class="rider-stats">
        <div class="stat">
          <span class="stat-value">{{ todayCompleted }}</span>
          <span class="stat-label">今日单量</span>
        </div>
        <div class="stat">
          <span class="stat-value">¥{{ totalEarnings }}</span>
          <span class="stat-label">今日收入</span>
        </div>
        <div class="stat">
          <span class="stat-value">{{ totalDistance }}km</span>
          <span class="stat-label">今日里程</span>
        </div>
      </div>
      <el-button class="logout-btn" @click="confirmLogout">
        🚪 退出
      </el-button>
    </div>

    <!-- 状态标签页 -->
    <el-tabs v-model="activeTab" class="status-tabs">
      <!-- 待配送 -->
      <el-tab-pane label="待配送" name="pending">
        <div class="order-list">
          <div class="order-card" v-for="order in pendingOrders" :key="order.id">
            <div class="order-header">
              <span class="order-no">{{ order.orderNo }}</span>
              <span class="order-badge pending">待配送</span>
            </div>
            <div class="order-info">
              <div class="info-row">
                <span class="label">🏪 商家</span>
                <span class="value">{{ order.storeName }}</span>
              </div>
              <div class="info-row">
                <span class="label">📍 地址</span>
                <span class="value">{{ order.address }}</span>
              </div>
              <div class="info-row">
                <span class="label">📞 电话</span>
                <span class="value">{{ order.phone }}</span>
              </div>
              <div class="info-row">
                <span class="label">💰 配送费</span>
                <span class="value">¥{{ order.deliveryFee }}</span>
              </div>
            </div>
            <div class="order-actions">
              <el-button type="primary" size="large" class="accept-btn" @click="startDelivery(order)">
                <span>🚀</span> 开始配送
              </el-button>
            </div>
          </div>
          <div v-if="pendingOrders.length === 0" class="empty-state">
            <div class="empty-icon">📭</div>
            <p>暂无待配送订单</p>
          </div>
        </div>
      </el-tab-pane>

      <!-- 配送中 -->
      <el-tab-pane label="配送中" name="delivering">
        <div class="order-list">
          <div class="order-card" v-for="order in deliveringOrders" :key="order.id">
            <div class="order-header">
              <span class="order-no">{{ order.orderNo }}</span>
              <span class="order-badge delivering">配送中</span>
            </div>
            <div class="order-info">
              <div class="info-row">
                <span class="label">🏪 商家</span>
                <span class="value">{{ order.storeName }}</span>
              </div>
              <div class="info-row">
                <span class="label">📍 地址</span>
                <span class="value">{{ order.address }}</span>
              </div>
              <div class="info-row">
                <span class="label">📞 电话</span>
                <span class="value">{{ order.phone }}</span>
              </div>
              <div class="info-row">
                <span class="label">⏱️ 剩余时间</span>
                <span class="value">{{ order.remainingTime }}分钟</span>
              </div>
            </div>
            <div class="order-actions">
              <el-button type="success" size="large" class="complete-btn" @click="completeDelivery(order)">
                <span>✅</span> 确认送达
              </el-button>
            </div>
          </div>
          <div v-if="deliveringOrders.length === 0" class="empty-state">
            <div class="empty-icon">🚚</div>
            <p>暂无配送中订单</p>
          </div>
        </div>
      </el-tab-pane>

      <!-- 已完成 -->
      <el-tab-pane label="已完成" name="completed">
        <div class="order-list">
          <div class="order-card completed" v-for="order in completedOrders" :key="order.id">
            <div class="order-header">
              <span class="order-no">{{ order.orderNo }}</span>
              <span class="order-badge completed">已完成</span>
            </div>
            <div class="order-info">
              <div class="info-row">
                <span class="label">🏪 商家</span>
                <span class="value">{{ order.storeName }}</span>
              </div>
              <div class="info-row">
                <span class="label">📍 地址</span>
                <span class="value">{{ order.address }}</span>
              </div>
              <div class="info-row">
                <span class="label">💰 收入</span>
                <span class="value">¥{{ order.deliveryFee }}</span>
              </div>
              <div class="info-row">
                <span class="label">📅 送达时间</span>
                <span class="value">{{ order.deliveryTime }}</span>
              </div>
            </div>
          </div>
          <div v-if="completedOrders.length === 0" class="empty-state">
            <div class="empty-icon">🎉</div>
            <p>暂无已完成订单</p>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 底部提示 -->
    <div class="footer-tip">
      <span>💡 提示：接单后请及时配送，超时会影响信用分</span>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()

// 骑手信息
const riderName = ref('李骑士')
const todayCompleted = ref(8)
const totalEarnings = ref(128)
const totalDistance = ref(45)

const activeTab = ref('pending')

// 待配送订单
const pendingOrders = ref([
  { 
    id: 1, 
    orderNo: '202406100001', 
    storeName: '快乐炸鸡店', 
    address: '教学楼A栋302', 
    phone: '138****0001',
    deliveryFee: 5
  },
  { 
    id: 2, 
    orderNo: '202406100002', 
    storeName: '奶茶咖啡店', 
    address: '宿舍楼5栋201', 
    phone: '138****0002',
    deliveryFee: 5
  },
  { 
    id: 3, 
    orderNo: '202406100003', 
    storeName: '川湘小炒', 
    address: '图书馆2楼', 
    phone: '138****0003',
    deliveryFee: 6
  }
])

// 配送中订单
const deliveringOrders = ref([
  { 
    id: 4, 
    orderNo: '202406090015', 
    storeName: '必胜客', 
    address: '教学楼B栋101', 
    phone: '138****0004',
    deliveryFee: 5,
    remainingTime: 18
  }
])

// 已完成订单
const completedOrders = ref([
  { 
    id: 5, 
    orderNo: '202406090010', 
    storeName: '肯德基', 
    address: '行政楼3楼', 
    phone: '138****0005', 
    deliveryFee: 5,
    deliveryTime: '2024-06-09 12:30' 
  },
  { 
    id: 6, 
    orderNo: '202406090008', 
    storeName: '星巴克', 
    address: '图书馆1楼', 
    phone: '138****0006', 
    deliveryFee: 4,
    deliveryTime: '2024-06-09 11:20' 
  }
])

// 开始配送
const startDelivery = (order) => {
  ElMessage.success(`已接单，开始配送订单 ${order.orderNo}`)
  const index = pendingOrders.value.indexOf(order)
  pendingOrders.value.splice(index, 1)
  deliveringOrders.value.push({ 
    ...order, 
    remainingTime: 30,
    startTime: new Date().toLocaleTimeString()
  })
}

// 确认送达
const completeDelivery = (order) => {
  ElMessage.success(`订单 ${order.orderNo} 已送达，获得 ¥${order.deliveryFee} 配送费`)
  const index = deliveringOrders.value.indexOf(order)
  deliveringOrders.value.splice(index, 1)
  completedOrders.value.unshift({ 
    ...order, 
    deliveryTime: new Date().toLocaleString()
  })
  todayCompleted.value++
  totalEarnings.value += order.deliveryFee
  totalDistance.value += 3
}

// 退出登录确认
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
</script>

<style scoped>
.rider-delivery {
  padding: 0;
}

/* 顶部骑手信息栏 */
.rider-top-bar {
  background: white;
  border-radius: 20px;
  padding: 20px 24px;
  margin-bottom: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 12px rgba(0,0,0,0.05);
}

.rider-info {
  display: flex;
  align-items: center;
  gap: 16px;
}

.rider-avatar {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #FFE4B5 0%, #FFD699 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
}

.rider-name {
  font-size: 18px;
  font-weight: bold;
  color: #D2691E;
}

.rider-badge {
  font-size: 12px;
  color: #FF8C42;
  margin-top: 4px;
}

.rider-stats {
  display: flex;
  gap: 32px;
}

.stat {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 20px;
  font-weight: bold;
  color: #FF6B35;
}

.stat-label {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.logout-btn {
  background: #FFF4E6;
  border: 1px solid #FFE4B5;
  color: #FF6B35;
  border-radius: 20px;
  padding: 8px 20px;
}

.logout-btn:hover {
  background: #FFE4B5;
}

/* 标签页 */
.status-tabs {
  background: white;
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 24px;
}

.order-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-height: 300px;
}

.order-card {
  background: #FFF8F0;
  border-radius: 16px;
  padding: 20px;
  transition: all 0.3s;
  border: 1px solid #FFE4B5;
}

.order-card:hover {
  box-shadow: 0 4px 12px rgba(255, 140, 66, 0.1);
  transform: translateX(4px);
}

.order-card.completed {
  background: #FAFAFA;
  opacity: 0.85;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #FFE4B5;
}

.order-no {
  font-size: 16px;
  font-weight: bold;
  color: #D2691E;
}

.order-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  color: white;
}

.order-badge.pending {
  background: #FF8C42;
}

.order-badge.delivering {
  background: #1890FF;
}

.order-badge.completed {
  background: #52C41A;
}

.order-info {
  margin-bottom: 16px;
}

.info-row {
  display: flex;
  margin-bottom: 8px;
}

.info-row .label {
  width: 70px;
  color: #999;
  font-size: 13px;
}

.info-row .value {
  flex: 1;
  color: #333;
  font-size: 14px;
}

.order-actions {
  text-align: right;
}

.accept-btn, .complete-btn {
  background: linear-gradient(135deg, #FF8C42 0%, #FF6B35 100%);
  border: none;
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
}

.empty-icon {
  font-size: 64px;
  margin-bottom: 16px;
  opacity: 0.5;
}

.empty-state p {
  color: #999;
  font-size: 14px;
}

.footer-tip {
  background: #FFF4E6;
  border-radius: 12px;
  padding: 12px 20px;
  text-align: center;
  font-size: 12px;
  color: #FF8C42;
}

/* 响应式 */
@media (max-width: 768px) {
  .rider-top-bar {
    flex-direction: column;
    gap: 16px;
  }
  
  .rider-stats {
    gap: 20px;
  }
}
</style>