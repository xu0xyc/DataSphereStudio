/*
 *
 *  * Copyright 2019 WeBank
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.webank.wedatasphere.dss.workflow.appconn.opertion;

import com.webank.wedatasphere.dss.common.protocol.RequestDeleteWorkflow;
import com.webank.wedatasphere.dss.standard.app.development.DevelopmentService;
import com.webank.wedatasphere.dss.standard.app.development.crud.RefDeletionOperation;
import com.webank.wedatasphere.dss.standard.common.entity.ref.RequestRef;
import com.webank.wedatasphere.dss.standard.common.entity.ref.ResponseRef;
import com.webank.wedatasphere.dss.standard.common.exception.operation.ExternalOperationFailedException;
import com.webank.wedatasphere.dss.workflow.appconn.ref.WorkflowDeleteRequestRef;
import com.webank.wedatasphere.dss.workflow.appconn.utils.RPCHelper;
import com.webank.wedatasphere.linkis.rpc.Sender;

/**
 * @author allenlliu
 * @date 2020/10/21 05:20 PM
 */
public class WorkflowTaskDeletionOperation implements RefDeletionOperation<WorkflowDeleteRequestRef, ResponseRef> {

    private DevelopmentService developmentService;


    @Override
    public void deleteRef(WorkflowDeleteRequestRef workflowDeleteRequestRef) throws ExternalOperationFailedException {
        String userName = workflowDeleteRequestRef.getUserName();
        Long flowId = workflowDeleteRequestRef.getAppId();
        RequestDeleteWorkflow requestDeleteWorkflow = new RequestDeleteWorkflow(userName, flowId);
        Sender sender = RPCHelper.getRpcSenderByAppInstance(developmentService.getAppInstance());
        if (null != sender) {
            sender.ask(requestDeleteWorkflow);
        } else {
            throw new ExternalOperationFailedException(100036, "Rpc sender is null", null);
        }
    }

    @Override
    public void setDevelopmentService(DevelopmentService service) {
        this.developmentService = service;
    }
}
