let api = [];
api.push({
    alias: 'OnceInitController',
    order: '1',
    link: '系统初始化',
    desc: '系统初始化',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '对admin菜单进行授权',
});
api.push({
    alias: 'PermissionController',
    order: '2',
    link: '权限控制',
    desc: '权限控制',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '获取权限包装类',
});
api[1].list.push({
    order: '2',
    desc: '根据资源类别和角色获取权限',
});
api[1].list.push({
    order: '3',
    desc: '保存权限',
});
api[1].list.push({
    order: '4',
    desc: '删除权限',
});
api.push({
    alias: 'ResourceRangeController',
    order: '3',
    link: '资源范围',
    desc: '资源范围',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '获取资源类型',
});
api[2].list.push({
    order: '2',
    desc: '通过角色和资源类型获取资源范围',
});
api[2].list.push({
    order: '3',
    desc: '检查资源范围是否存在',
});
api[2].list.push({
    order: '4',
    desc: '保存资源范围',
});
api[2].list.push({
    order: '5',
    desc: '删除资源范围',
});
api.push({
    alias: 'ResourceTypeController',
    order: '4',
    link: '资源类型',
    desc: '资源类型',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '获取资源类型（分页，查询）',
});
api[3].list.push({
    order: '2',
    desc: '获取一个资源类型',
});
api[3].list.push({
    order: '3',
    desc: '保存',
});
api[3].list.push({
    order: '4',
    desc: '删除',
});
api[3].list.push({
    order: '5',
    desc: '检查资源类型类名是否存在',
});
api.push({
    alias: 'RoleController',
    order: '5',
    link: '角色',
    desc: '角色',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '获取全部角色',
});
api[4].list.push({
    order: '2',
    desc: '获取角色',
});
api[4].list.push({
    order: '3',
    desc: '获取一个角色',
});
api[4].list.push({
    order: '4',
    desc: '检查角色id是否存在',
});
api[4].list.push({
    order: '5',
    desc: '保存角色信息',
});
api[4].list.push({
    order: '6',
    desc: '删除角色',
});
api[4].list.push({
    order: '7',
    desc: '设置角色的用户',
});
api.push({
    alias: 'UserController',
    order: '6',
    link: '用户',
    desc: '用户',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '获取用户',
});
api[5].list.push({
    order: '2',
    desc: '获取用户信息',
});
api[5].list.push({
    order: '3',
    desc: '检查用户是否存在',
});
api[5].list.push({
    order: '4',
    desc: '获取指定角色的用户',
});
api[5].list.push({
    order: '5',
    desc: '获取指定角色的用户',
});
api[5].list.push({
    order: '6',
    desc: '获取所有有效用户（未删除的用户）',
});
api[5].list.push({
    order: '7',
    desc: '保存',
});
api[5].list.push({
    order: '8',
    desc: '设置用户密码',
});
api[5].list.push({
    order: '9',
    desc: '检查用户密码',
});
api[5].list.push({
    order: '10',
    desc: '删除用户',
});
api.push({
    alias: 'AuthenticationApi',
    order: '7',
    link: '用户认证',
    desc: '用户认证',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '客户端刷新服务器认证接口',
});
api.push({
    alias: 'FieldAuditContoller',
    order: '8',
    link: '现场审核信息',
    desc: '现场审核信息',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '',
});
api[7].list.push({
    order: '2',
    desc: '保存现场审核意见',
});
api[7].list.push({
    order: '3',
    desc: '删除现场审核意见',
});
api.push({
    alias: 'MenuController',
    order: '9',
    link: '菜单',
    desc: '菜单',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '返回顶级菜单',
});
api[8].list.push({
    order: '2',
    desc: '返回有权限的菜单树',
});
api[8].list.push({
    order: '3',
    desc: '保存菜单',
});
api[8].list.push({
    order: '4',
    desc: '删除菜单',
});
api.push({
    alias: 'MyResourceController',
    order: '10',
    link: '我的资源',
    desc: '我的资源',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '获取资源',
});
api[9].list.push({
    order: '2',
    desc: '获取资源',
});
api[9].list.push({
    order: '3',
    desc: '获取单个资源',
});
api[9].list.push({
    order: '4',
    desc: '删除资源',
});
api[9].list.push({
    order: '5',
    desc: '保存资源',
});
api[9].list.push({
    order: '6',
    desc: '保存签名信息',
});
api[9].list.push({
    order: '7',
    desc: '办结资源',
});
api.push({
    alias: 'MyResourceRoomController',
    order: '11',
    link: '我的资源-房间明细',
    desc: '我的资源-房间明细',
    list: []
})
api[10].list.push({
    order: '1',
    desc: '保存',
});
api[10].list.push({
    order: '2',
    desc: '获取一个房间明细',
});
api[10].list.push({
    order: '3',
    desc: '获取我的资源对应的房间',
});
api[10].list.push({
    order: '4',
    desc: '删除房间',
});
api.push({
    alias: 'WeixingResourceController',
    order: '12',
    link: '卫星场地',
    desc: '卫星场地',
    list: []
})
api[11].list.push({
    order: '1',
    desc: '获取卫星场地信息',
});
api[11].list.push({
    order: '2',
    desc: '获取资源',
});
api[11].list.push({
    order: '3',
    desc: '获取单个卫星场地信息',
});
api[11].list.push({
    order: '4',
    desc: '删除卫星场地信息',
});
api[11].list.push({
    order: '5',
    desc: '保存',
});
api.push({
    alias: 'WeixingResourceFieldAuditController',
    order: '13',
    link: '卫星场地-现场审核信息',
    desc: '卫星场地-现场审核信息',
    list: []
})
api[12].list.push({
    order: '1',
    desc: '保存卫星场地审核信息',
});
api[12].list.push({
    order: '2',
    desc: '根据卫星场地id获取现场审核意见',
});
api[12].list.push({
    order: '3',
    desc: '删除卫星场地现场审核意见',
});
api.push({
    alias: 'FileController',
    order: '14',
    link: '文件上传，删除等操作',
    desc: '文件上传，删除等操作',
    list: []
})
api[13].list.push({
    order: '1',
    desc: '上传文件按照年月分文件夹存储',
});
api[13].list.push({
    order: '2',
    desc: '删除文件',
});
api.push({
    alias: 'dict',
    order: '15',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}