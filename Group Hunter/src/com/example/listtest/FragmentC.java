package com.example.listtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentC extends Fragment {
@Override
public View onCreateView(LayoutInflater inflater,
		@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	//return super.onCreateView(inflater, container, savedInstanceState);
	return inflater.inflate(R.layout.fragment_c, container,false);
}
}