/*
 * Copyright (c) 2011. Rush Project Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.worktoken.tasklist;

import com.worktoken.annotation.FlowElement;
import com.worktoken.model.Connector;
import com.worktoken.model.ScriptTask;
import com.worktoken.model.WorkToken;

/**
 * @author Alex Pavlov (alex@rushproject.com)
 */
@FlowElement(nodeRef = "archiveTask", processId = "taskList")
public class ArchiveTask extends ScriptTask {

    @Override
    public void tokenIn(WorkToken token, Connector connector) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setSubject(token.getData().get("subject").toString());
        taskRecord.setNotes(token.getData().get("notes").toString());
        getSession().persist(taskRecord);
        tokenOut();
    }
}
