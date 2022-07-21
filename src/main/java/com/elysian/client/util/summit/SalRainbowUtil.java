
package com.elysian.client.util.summit;

import java.util.ArrayList;

public class SalRainbowUtil
{
    private ArrayList<Integer> CurrentRainbowIndexes;
    private ArrayList<Integer> RainbowArrayList;
    private Timer RainbowSpeed;
    private int m_Timer;
    
    public SalRainbowUtil(final int p_Timer) {
        CurrentRainbowIndexes = new ArrayList<Integer>();
        RainbowArrayList = new ArrayList<Integer>();
        RainbowSpeed = new Timer();
        m_Timer = p_Timer;
        for (int l_I = 0; l_I < 360; ++l_I) {
            RainbowArrayList.add(ColorUtil.GetRainbowColor((float)l_I, 90.0f, 50.0f, 1.0f).getRGB());
            CurrentRainbowIndexes.add(l_I);
        }
    }
    
    public int GetRainbowColorAt(int p_Index) {
        if (p_Index > CurrentRainbowIndexes.size() - 1) {
            p_Index = CurrentRainbowIndexes.size() - 1;
        }
        return RainbowArrayList.get(CurrentRainbowIndexes.get(p_Index));
    }
    
    public void SetTimer(final int p_NewTimer) {
        m_Timer = p_NewTimer;
    }
    
    public void OnRender() {
        if (RainbowSpeed.passed(m_Timer)) {
            RainbowSpeed.reset();
            MoveListToNextColor();
        }
    }
    
    private void MoveListToNextColor() {
        if (CurrentRainbowIndexes.isEmpty()) {
            return;
        }
        CurrentRainbowIndexes.remove(CurrentRainbowIndexes.get(0));
        int l_Index = CurrentRainbowIndexes.get(CurrentRainbowIndexes.size() - 1) + 1;
        if (l_Index >= RainbowArrayList.size() - 1) {
            l_Index = 0;
        }
        CurrentRainbowIndexes.add(l_Index);
    }
}
