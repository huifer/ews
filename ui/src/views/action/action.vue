<template>
  <div>
    <div id="action_query">
      <el-form
        :model="action_query"
        class="form"
        label-position="right"
        label-width="120px"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item label="场景名称" prop="scenesName">
              <el-input v-model="action_query.scenesName" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="请求方式" prop="httpMethod">
              <el-input v-model="action_query.httpMethod" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="路由地址" prop="url">
              <el-input v-model="action_query.url" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button circle icon="el-icon-search" type="primary" @click="query"></el-button>
            <el-button type="primary" @click="dialogVisible_action_create = true">新增</el-button>
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
          prop="scenesName"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="路由地址"
          prop="url"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="请求方式"
          prop="httpMethod"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="实例"
          prop="example"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="操作"
        >
          <template slot-scope="scope">
            <el-button size="small" type="text" @click="show_action_param_table(scope.row)">展示参数</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>


    <el-dialog
      :before-close="handleClose"
      :visible.sync="dialogVisible_action_create"
      title="创建场景"
      width="75%"
    >
      <el-form :model="action_create">

        <el-form-item :label-width="name" label="场景名称">
          <el-select v-model="action_create.action.scenesId" placeholder="请选择">
            <el-option
              v-for="item in scenes_id"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item :label-width="httpMethod" label="请求方式">
          <el-select v-model="action_create.action.httpMethod" placeholder="请选择">
            <el-option
              v-for="item in httpMethod"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label-width="url" label="请求地址">
          <el-input v-model="action_create.action.url" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item :label-width="example" label="请求实例">
          <el-input v-model="action_create.action.example" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>


      <el-button type="primary" @click="dialogVisible_action_param_create = true">新增参数</el-button>

      <el-table
        :data="action_create.list"
        style="width: 100%"
      >
        <el-table-column
          label="值提取式"
          prop="expression"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="目标建"
          prop="target"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="默认值"
          prop="defaultValue"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="操作"
        >
          <template slot-scope="scope">
            <el-button size="small" type="text" @click="remove_table(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible_action_create = false">取 消</el-button>
        <el-button type="primary" @click="action_create_api">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog
      :before-close="handleClosePram"
      :visible.sync="dialogVisible_action_param_create"
      title="创建参数"
      width="75%"
    >

      <el-form :model="add_action_param">

        <el-form-item :label-width="expression" label="值提取式">
          <el-input v-model="add_action_param.expression" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item :label-width="target" label="目标键">
          <el-input v-model="add_action_param.target" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item :label-width="defaultValue" label="默认值">
          <el-input v-model="add_action_param.defaultValue" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>


      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible_action_param_create = false">取 消</el-button>
        <el-button type="primary" @click="addParam">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog
      :visible.sync="show_action_dg"
      title="动作参数"
      width="75%"
    >
      <el-table
        :data="action_param_list"
        style="width: 100%"
      >
        <el-table-column
          label="值提取式"
          prop="expression"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="目标建"
          prop="target"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="默认值"
          prop="defaultValue"
          width="180"
        >
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { actionAdd, actionQuery } from '@/api/action'
import { scenesList } from '@/api/scenes'
import { actionParamList } from '@/api/actionParam'

export default {
  name: 'Action',
  data() {
    return {
      show_action_dg: false,
      action_query: {
        scenesName: '',
        httpMethod: '',
        url: '',
        pageSize: 10,
        pageNumber: 0
      },
      tableData: [],
      dialogVisible_action_create: false,
      dialogVisible_action_param_create: false,
      action_create: {
        action: {
          scenesId: '',
          httpMethod: '',
          url: '',
          example: ''
        },
        list: []
      },
      scenes_id: [],
      httpMethod: [
        {
          value: 'POST_FORM',
          label: 'POST_FORM'
        },
        {
          value: 'POST_JSON',
          label: 'POST_JSON'
        },
        {
          value: 'GET',
          label: 'GET'
        }
      ],
      add_action_param: {
        expression: '',
        target: '',
        defaultValue: ''
      },
      action_param_list: []
    }
  },
  methods: {
    uuid() {
      var s = []
      var hexDigits = '0123456789abcdef'
      for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
      }
      s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
      s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
      s[8] = s[13] = s[18] = s[23] = '-'

      var uuid = s.join('')
      return uuid
    },
    addParam() {
      this.dialogVisible_action_param_create = false
      console.log(this.add_action_param)

      this.action_create.list.push({
        uid: this.uuid(),
        expression: this.add_action_param.expression,
        target: this.add_action_param.target,
        defaultValue: this.add_action_param.defaultValue
      })
    },
    action_create_api() {
      this.dialogVisible_action_create = false
      actionAdd(this.action_create).then(
        res => {
          this.query()

        }
      )
    },
    handleClosePram(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    },
    showActionParamTableClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    },
    query() {
      actionQuery({
        scenesName: this.action_query.scenesName,
        httpMethod: this.action_query.httpMethod,
        url: this.action_query.url,
        pageSize: this.action_query.pageSize,
        pageNumber: this.action_query.pageNumber
      }).then(res => {
        this.tableData = res.data.records
      })

    },
    remove_table(row) {
      this.action_create.list = this.action_create.list.filter(v => v.uid !== row.uid)
    },
    init_scenes() {
      scenesList().then(
        res => {
          this.scenes_id = res.data
        }
      )
    },
    show_action_param_table(row) {
      console.log(row)
      this.show_action_dg = true
      actionParamList({ actionId: row.id }).then(
        res => {
          console.log(res)
          this.action_param_list = res.data
        }
      )
    }
  }, created() {
    this.query()
    this.init_scenes()

  }

}
</script>

<style scoped>

</style>
