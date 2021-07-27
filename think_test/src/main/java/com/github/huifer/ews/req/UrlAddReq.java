package com.github.huifer.ews.req;

import com.github.huifer.ews.entity.ParamEntity;
import com.github.huifer.ews.entity.UrlEntity;
import lombok.Data;

import java.util.List;

@Data
public class UrlAddReq {
	private UrlEntity urlEntity;
	private List<ParamEntity> paramEntities;
}
