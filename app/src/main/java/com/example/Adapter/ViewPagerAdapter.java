//package com.example.Adapter;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import com.example.fragment.LoginFragment;
//import com.example.fragment.RegisterFragment;
//
//public class ViewPagerAdapter extends FragmentPagerAdapter {
//    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return new LoginFragment();
//
//            case 1:
//                return new RegisterFragment();
//
//            default:
//                return new LoginFragment();
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return 2;
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = "";
//        switch (position) {
//            case 0:
//                title = "Đăng Nhập";
//                break;
//            case 1:
//                title = "Đăng Ký";
//                break;
//        }
//        return title;
//    }
//}
