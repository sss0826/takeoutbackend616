<template>
  <div class="merchant-dish">
    <div class="page-header">
      <h2>🍔 菜品管理</h2>
      <p>管理您的菜单，让顾客看到美味</p>
    </div>

    <!-- 操作栏 -->
    <div class="action-bar">
      <el-button type="primary" size="large" class="add-btn" @click="addDish">
        <span>+</span> 新增菜品
      </el-button>
      <el-input 
        v-model="searchKeyword" 
        placeholder="搜索菜品..." 
        prefix-icon="Search"
        style="width: 250px"
        size="large"
      />
    </div>

    <!-- 菜品列表 -->
    <div class="dish-grid">
      <div class="dish-card" v-for="dish in filteredDishes" :key="dish.id">
        <div class="dish-img" :style="{ backgroundImage: `url(${dish.img})` }">
          <span class="dish-status" :class="dish.status === '在售' ? 'on-sale' : 'off-sale'">
            {{ dish.status }}
          </span>
        </div>
        <div class="dish-info">
          <h3>{{ dish.name }}</h3>
          <p class="dish-price">¥{{ dish.price }}</p>
          <p class="dish-sales">月销 {{ dish.sales }}+</p>
          <div class="dish-actions">
            <el-button size="small" @click="editDish(dish)">编辑</el-button>
            <el-button size="small" type="danger" plain @click="deleteDish(dish)">删除</el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="dishForm" label-width="80px">
        <el-form-item label="菜品名称">
          <el-input v-model="dishForm.name" placeholder="请输入菜品名称" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="dishForm.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="菜品描述">
          <el-input type="textarea" v-model="dishForm.description" rows="3" placeholder="请输入菜品描述" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="dishForm.status">
            <el-radio label="在售">在售</el-radio>
            <el-radio label="售罄">售罄</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveDish">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'

const searchKeyword = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('新增菜品')
const editingId = ref(null)

const dishForm = ref({
  name: '',
  price: 0,
  description: '',
  status: '在售'
})

const dishList = ref([
  { id: 1, name: '香辣鸡腿堡', price: 18, status: '在售', sales: 2345, img: 'https://images.pexels.com/photos/2983101/pexels-photo-2983101.jpeg?w=200&h=130&fit=crop' },
  { id: 2, name: '薯条', price: 10, status: '在售', sales: 1890, img: 'https://images.pexels.com/photos/1583884/pexels-photo-1583884.jpeg?w=200&h=130&fit=crop' },
  { id: 3, name: '可乐', price: 6, status: '售罄', sales: 1567, img: 'https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?w=200&h=130&fit=crop' },
  { id: 4, name: '鸡米花', price: 12, status: '在售', sales: 1234, img: 'https://images.pexels.com/photos/1583884/pexels-photo-1583884.jpeg?w=200&h=130&fit=crop' }
])

const filteredDishes = computed(() => {
  if (!searchKeyword.value) return dishList.value
  return dishList.value.filter(dish => 
    dish.name.includes(searchKeyword.value)
  )
})

const addDish = () => {
  dialogTitle.value = '新增菜品'
  editingId.value = null
  dishForm.value = { name: '', price: 0, description: '', status: '在售' }
  dialogVisible.value = true
}

const editDish = (dish) => {
  dialogTitle.value = '编辑菜品'
  editingId.value = dish.id
  dishForm.value = { ...dish }
  dialogVisible.value = true
}

const saveDish = () => {
  if (!dishForm.value.name || dishForm.value.price <= 0) {
    ElMessage.warning('请填写完整的菜品信息')
    return
  }
  
  if (editingId.value) {
    const index = dishList.value.findIndex(d => d.id === editingId.value)
    dishList.value[index] = { ...dishForm.value, id: editingId.value, sales: dishList.value[index].sales }
    ElMessage.success('菜品已更新')
  } else {
    const newId = Math.max(...dishList.value.map(d => d.id)) + 1
    dishList.value.push({ ...dishForm.value, id: newId, sales: 0, img: 'https://picsum.photos/200/130?random=' + newId })
    ElMessage.success('菜品已添加')
  }
  dialogVisible.value = false
}

const deleteDish = (dish) => {
  ElMessage.success(`已删除 ${dish.name}`)
}
</script>

<style scoped>
.merchant-dish {
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

.action-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.add-btn {
  background: linear-gradient(135deg, #FF8C42 0%, #FF6B35 100%);
  border: none;
}

.dish-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
}

.dish-card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.3s;
  box-shadow: 0 2px 12px rgba(0,0,0,0.05);
}

.dish-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(255, 140, 66, 0.15);
}

.dish-img {
  height: 140px;
  background-size: cover;
  background-position: center;
  position: relative;
}

.dish-status {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  color: white;
}

.dish-status.on-sale {
  background: #52C41A;
}

.dish-status.off-sale {
  background: #999;
}

.dish-info {
  padding: 16px;
}

.dish-info h3 {
  font-size: 16px;
  margin: 0 0 8px 0;
  color: #D2691E;
}

.dish-price {
  font-size: 20px;
  font-weight: bold;
  color: #FF6B35;
  margin: 8px 0;
}

.dish-sales {
  font-size: 12px;
  color: #999;
  margin: 4px 0;
}

.dish-actions {
  display: flex;
  gap: 12px;
  margin-top: 12px;
}
</style>