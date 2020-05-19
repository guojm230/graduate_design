using System;
using System.Collections.Generic;
using System.Text;

namespace design_client.Model.Mate
{
    public abstract class MateVertex
    {
        public MateEdge? FirstEdge { get; set; }

        public MateVertex(){}

        /// <summary>
        /// 是否支持该配合的接口
        /// </summary>
        /// <param name="mateInterface"></param>
        /// <returns></returns>
        public abstract bool CanSupport(MateInterface mateInterface);

        public abstract MateInterface Support(MateInterface mateInterface);

        /// <summary>
        /// 是否能配合改接口,参数是否能符合
        /// </summary>
        /// <param name="mateInterface"></param>
        /// <returns></returns>
        public abstract bool CanMate(MateInterface mateInterface);

        //进行配合测试，如果参数符合则返回配合的MateInterface，否则返回null
        public abstract MateInterface? Mate(MateInterface mateInterface);
    }
}
