<template>
  <div>
    <div id="rule_query">
      <el-form
        :model="process_query"
        class="form"
        label-position="right"
        label-width="120px"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item :label-width="name" label="场景名称">
              <el-select v-model="process_query.scenesId" placeholder="请选择">
                <el-option
                  v-for="item in scenes_id"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="流程名称" prop="description">
              <el-input v-model="process_query.name" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button circle icon="el-icon-search" type="primary" @click="queryProcess"></el-button>
            <el-button type="primary" @click="dialogVisible_process_create = true">新增</el-button>
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
          prop="sceneName"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="流程名称"
          prop="name"
          width="180"
        >
        </el-table-column>

        <el-table-column
          label="流程描述"
          prop="description"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="条件表达式"
          prop="expression"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="操作"
        >
          <template slot-scope="scope">
            <el-button size="small" type="text" @click="show_rule(scope.row)">规则详情</el-button>
            <el-button size="small" type="text" @click="show_secces(scope.row)">查看成功行动</el-button>
            <el-button size="small" type="text" @click="show_faild(scope.row)">查看失败</el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>


    <el-dialog
      :visible.sync="show_rule_dialog"
      title="规则详情"
      width="75%"
    >
      <el-table
        :data="rule_data"
        style="width: 100%"
      >
        <el-table-column
          label="规则ID"
          prop="id"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="规则名称"
          prop="name"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="取值表达式"
          prop="expression"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="运算符"
          prop="opStr"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="比较值"
          prop="comparisonValue"
        >
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog
      :visible.sync="show_action_dialog"
      title="行动详情"
      width="75%"
    >

      <div id="show_action_table">
        <el-table
          :data="action_table"
          style="width: 100%"
        >


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

        </el-table>
      </div>
    </el-dialog>

    <el-dialog
      :visible.sync="dialogVisible_process_create"
      title="新增处理策略"
      width="75%"
    >
      <el-form>
        <el-row>
          <el-col :span="8">
            <el-form-item :label-width="name" label="场景名称">
              <el-select v-model="process_create.process.scenesId" placeholder="请选择" @change="init_action_list">
                <el-option
                  v-for="item in scenes_id"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="流程名称" prop="description">
              <el-input v-model="process_create.process.name" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="流程描述" prop="description">
              <el-input v-model="process_create.process.description" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="条件表达式" prop="description">
              <el-input v-model="process_create.process.expression" placeholder="请输入"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="成功动作" prop="description">
              <el-select v-model="process_create.trueActions" multiple placeholder="请选择">
                <el-option
                  v-for="item in action_list"
                  :key="item.url"
                  :label="item.url"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="失败动作" prop="description">
              <el-select v-model="process_create.falseActions" multiple placeholder="请选择">
                <el-option
                  v-for="item in action_list"
                  :key="item.url"
                  :label="item.url"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-button type="primary" @click="showRuleInfoWithAdd">查看规则</el-button>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible_process_create = false">取 消</el-button>
        <el-button type="primary" @click="process_create_api">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { scenesList } from '@/api/scenes'
import { processAdd, processPFalse, processPTrue, processQuery } from '@/api/process'
import { ruleExp, ruleScId } from '@/api/rule'
import { actionList } from '@/api/action'

export default {
  name: 'Process',
  data() {
    return {
      scenes_id: [],
      process_query: {
        scenesId: '',
        name: '',
        description: ''
      },
      dialogVisible_process_create: false,
      show_rule_dialog: false,
      tableData: [],
      rule_data: [],
      action_table: [],
      show_action_dialog: false,
      process_create: {
        process: {
          scenesId: '',
          expression: '',
          name: '',
          description: ''
        },
        trueActions: [],
        falseActions: []
      },
      action_list: []
    }
  },
  methods: {
    showRuleInfoWithAdd() {
      this.show_rule_dialog = true
      if (this.process_create.process.scenesId) {

        ruleScId({ scId: this.process_create.process.scenesId })
          .then(res => {
            this.rule_data = res.data

          })
      }
    },
    process_create_api() {
      this.dialogVisible_process_create = false
      console.log(this.process_create)
      processAdd(this.process_create)
    },
    queryProcess() {
      processQuery(
        {
          scenesId: this.process_query.scenesId,
          name: this.process_query.name,
          description: this.process_query.description
        }
      ).then(res => {
        this.tableData = res.data
      })
    },
    init_scenes() {
      scenesList().then(
        res => {
          this.scenes_id = res.data
        }
      )
    },
    show_rule(row) {
      this.show_rule_dialog = true

      ruleExp({ exp: row.expression }).then(res => {
        console.log(res)
        this.rule_data = res.data
      })

    },
    show_secces(row) {
      this.show_action_dialog = true
      processPTrue({ processId: row.id }).then(res => {
        console.log(res)
        this.action_table = res.data
      })
    },
    show_faild(row) {
      this.show_action_dialog = true
      processPFalse({ processId: row.id }).then(res => {
        console.log(res)
        this.action_table = res.data
      })
    },
    get_action_list(scId) {
      actionList({
        scId: scId
      }).then(res => {
        this.action_list = res.data
      })
    },
    init_action_list(es) {
      console.log(es)
      this.get_action_list(es)
      this.process_create.trueActions = []
      this.process_create.falseActions = []
    }
  }, created() {
    this.queryProcess()
    this.init_scenes()
  },
  watch: {}
}
</script>

<style scoped>

</style>
