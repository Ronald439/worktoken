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

package com.worktoken.engine.test.helpdesk;

import com.worktoken.annotation.FlowElement;
import com.worktoken.annotation.RefType;
import com.worktoken.model.Connector;
import com.worktoken.model.UserTask;
import com.worktoken.model.WorkToken;

import javax.persistence.Entity;

/**
 * @author Alex Pavlov (alex@rushproject.com)
 */
@FlowElement(nodeRef = "Prepare answer", refType = RefType.Name, processId = "process-com_worktoken_helpdesk")
@Entity
public class PrepareAnswer extends UserTask {

    private String answer;
    private static final String view = "/helpdesk/prepare-answer.xhtml";

    @Override
    public void tokenIn(WorkToken token, Connector connector) {
    }

    @Override
    public String getSubject() {
        return ((HelpDeskProcess) getProcess()).getSubject();
    }

    @Override
    public String getDocumentation() {
        return "Prepare answer";
    }

    @Override
    public String getView() {
        return view;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void complete() {
        WorkToken token = new WorkToken();
        token.getData().put("answer", answer);
        tokenOut(token);
    }

    public HelpDeskProcess getHelpdeskProcess() {
        return (HelpDeskProcess) getProcess();
    }
}
