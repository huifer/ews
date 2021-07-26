package com.github.huifer.ews.domain.dto;

import com.github.huifer.ews.domain.db.Scenes;
import lombok.Data;

import java.util.List;

@Data
public class ScenesFull {
	private Scenes scenes;
	private List<ProcessFull> processFulls;
}
