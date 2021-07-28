<template>
  <div>
    <div id="scenes_query">
      <el-form
        ref="organForm"
        :model="scenes_query"
        class="form"
        label-position="right"
        label-width="120px"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item label="场景名称" prop="name">
              <el-input v-model="scenes_query.name" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="场景描述" prop="description">
              <el-input v-model="scenes_query.description" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button circle icon="el-icon-search" type="primary" @click="query"></el-button>
            <el-button type="primary" @click="dialogVisible_scenes_create = true">新增</el-button>
          </el-col>

        </el-row>
      </el-form>
    </div>

    <div id="show_table">
      <el-table
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column
          label="场景名称"
          prop="name"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="场景描述"
          prop="description"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="示例json"
          prop="exampleJson"
        >
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
      :before-close="handleClose"
      :visible.sync="dialogVisible_scenes_create"
      title="创建场景"
      width="30%"
    >
      <el-form :model="scenes_create">
        <el-form-item :label-width="name" label="场景名称">
          <el-input v-model="scenes_create.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item :label-width="description" label="场景描述">
          <el-input v-model="scenes_create.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item :label-width="exampleJson" label="场景实例json">
          <el-input v-model="scenes_create.exampleJson" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible_scenes_create = false">取 消</el-button>
        <el-button type="primary" @click="scenes_create_api">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { scenesAdd, scenesQuery } from '@/api/scenes'

export default {
  name: 'Scenes',
  data() {
    return {
      scenes_query: {
        name: '',
        description: ''
      },
      organParams: {
        pageNo: 1,
        pageSize: 10
      },
      total: 0,
      tableData: [],
      dialogVisible_scenes_create: false,
      scenes_create: {
        name: '',
        exampleJson: '',
        description: ''
      }
    }
  },

  methods: {
    scenes_create_api() {
      this.dialogVisible_scenes_create = false
      console.log(this.scenes_create)
      scenesAdd(this.scenes_create)
      this.initData()
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    },
    initData() {
      scenesQuery({
        pageSize: 10,
        pageNumber: 0
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
      }).catch(e => {
        console.log(e)
      })
    },
    query() {
      scenesQuery({
        name: this.scenes_query.name,
        description: this.scenes_query.description,
        pageSize: 10,
        pageNumber: 0
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
      }).catch(e => {
        console.log(e)
      })

    }
  },
  created() {
    this.initData()

  }

}
</script>

<style scoped>

</style>
