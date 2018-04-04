package git.yampery.cryptic.common;

import git.yampery.cryptic.cryptic.ADESUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @decription QueryParams
 * <p>封装查询参数</p>
 * @author Yampery
 * @date 2018/4/4 14:23
 */
public class QueryParams extends LinkedHashMap<String, Object>{

    private static final long serialVersionUID = 1L;
    private static final String REGIX_PHONE = "^((0\\d{2,3}\\d{7,8})|(1[35784]\\d{9}))$";
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public QueryParams(Map<String, Object> params){
        // 如果参数是手机号，则加密后查询
        if (params.containsKey("phone")) {
            String number = params.get("phone").toString();
            if (number.matches(REGIX_PHONE)) {
                params.put("phone", ADESUtils.getInstance().encrypt(number));
            }
        }
        this.putAll(params);

        //分页参数
        if (null != params.get("page")) {
            this.page = Integer.parseInt(params.get("page").toString());
            this.put("page", page);
        }
        if (null != params.get("limit")) {
            this.limit = Integer.parseInt(params.get("limit").toString());
            this.put("limit", limit);
            this.put("offset", (page - 1) * limit);
        }
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
